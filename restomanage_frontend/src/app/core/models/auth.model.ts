export interface LoginRequest {
    username: string; // Can be username or email
    password: string;
}

export interface RegisterRequest {
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    password: string;
}

export interface ManagerRegisterRequest extends RegisterRequest {
    username: string;
}

export enum Role {
    CLIENT = 'CLIENT',
    MANAGER = 'MANAGER'
}

export interface AuthResponse {
    token: string;
    role: Role;
    userId: number;
    name: string;
}
