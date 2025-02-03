import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest, RegisterRequest, ManagerRegisterRequest, AuthResponse } from '../models/auth.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  clientLogin(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/client/login`, credentials);
  }

  managerLogin(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/manager/login`, credentials);
  }

  clientRegister(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/client/register`, data);
  }

  managerRegister(data: ManagerRegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/manager/register`, data);
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/logout`, {});
  }

  // Store the auth token
  setAuthToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Get the stored auth token
  getAuthToken(): string | null {
    return localStorage.getItem('token');
  }

  // Remove the stored auth token
  removeAuthToken(): void {
    localStorage.removeItem('token');
  }

  // Store user info
  setUserInfo(response: AuthResponse): void {
    localStorage.setItem('user', JSON.stringify({
      role: response.role,
      userId: response.userId,
      name: response.name
    }));
  }

  // Get stored user info
  getUserInfo(): { role: string; userId: number; name: string } | null {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  // Clear all auth data
  clearAuth(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  // Check if user is authenticated
  isAuthenticated(): boolean {
    return !!this.getAuthToken();
  }
}
