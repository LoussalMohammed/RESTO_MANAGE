import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="w-full">
      <label *ngIf="label" class="block text-sm font-medium text-gray-700 mb-1">
        {{label}}
        <span *ngIf="required" class="text-red-500">*</span>
      </label>

      <!-- Drop Zone -->
      <div
        class="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-dashed rounded-md"
        [ngClass]="{'border-gray-300': !isDragging, 'border-primary-500': isDragging}"
        (dragover)="onDragOver($event)"
        (dragleave)="onDragLeave($event)"
        (drop)="onDrop($event)">
        <div class="space-y-1 text-center">
          <span class="material-icons text-gray-400 text-3xl">cloud_upload</span>
          <div class="flex text-sm text-gray-600">
            <label
              class="relative cursor-pointer rounded-md font-medium text-primary-600 hover:text-primary-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-primary-500">
              <span>Upload a file</span>
              <input
                [id]="id"
                type="file"
                [accept]="accept"
                [multiple]="multiple"
                class="sr-only"
                (change)="onFileSelected($event)">
            </label>
            <p class="pl-1">or drag and drop</p>
          </div>
          <p class="text-xs text-gray-500">
            {{acceptedFileTypes}}
          </p>
        </div>
      </div>

      <!-- Preview -->
      <div *ngIf="files.length > 0" class="mt-4">
        <h4 class="text-sm font-medium text-gray-700">Selected files:</h4>
        <ul class="mt-2 divide-y divide-gray-200 border-t border-b">
          <li *ngFor="let file of files; let i = index" class="flex items-center justify-between py-3">
            <div class="flex items-center">
              <span class="material-icons text-gray-400 mr-2">description</span>
              <span class="text-sm text-gray-900">{{file.name}}</span>
              <span class="ml-2 text-sm text-gray-500">({{formatFileSize(file.size)}})</span>
            </div>
            <button
              type="button"
              class="text-red-500 hover:text-red-700"
              (click)="removeFile(i)">
              <span class="material-icons">delete</span>
            </button>
          </li>
        </ul>
      </div>

      <!-- Error Message -->
      <p *ngIf="error" class="mt-2 text-sm text-red-600">{{error}}</p>
    </div>
  `
})
export class FileUploadComponent {
  @Input() label?: string;
  @Input() id = 'file-upload';
  @Input() accept = '*/*';
  @Input() multiple = false;
  @Input() required = false;
  @Input() maxSize = 5242880; // 5MB
  @Input() error?: string;

  @Output() filesChanged = new EventEmitter<File[]>();

  files: File[] = [];
  isDragging = false;

  get acceptedFileTypes(): string {
    if (this.accept === '*/*') return 'Any file type up to 5MB';
    return `${this.accept.split(',').join(', ')} up to 5MB`;
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragging = true;
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragging = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragging = false;

    const files = event.dataTransfer?.files;
    if (files) {
      this.handleFiles(files);
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.handleFiles(input.files);
    }
  }

  handleFiles(fileList: FileList): void {
    const filesArray = Array.from(fileList);
    const validFiles = filesArray.filter(file => {
      if (file.size > this.maxSize) {
        this.error = 'File size exceeds 5MB limit';
        return false;
      }
      if (this.accept !== '*/*' && !this.accept.split(',').some(type => file.type.match(type))) {
        this.error = 'Invalid file type';
        return false;
      }
      return true;
    });

    if (this.multiple) {
      this.files = [...this.files, ...validFiles];
    } else {
      this.files = validFiles.slice(0, 1);
    }

    this.filesChanged.emit(this.files);
  }

  removeFile(index: number): void {
    this.files.splice(index, 1);
    this.filesChanged.emit(this.files);
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }
}
