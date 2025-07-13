import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  isAuthenticated = signal(false);
  authToken = signal<string | null>(null);

  isLoggedIn() {
    return this.isAuthenticated();
  }

  login(token: string) {
    this.authToken.set(token);
    this.isAuthenticated.set(true);
    localStorage.setItem('authToken', token);
  }

  logout() {
    this.authToken.set(null);
    this.isAuthenticated.set(false);
    localStorage.removeItem('authToken');
  }

  getToken() {
    return this.authToken() || localStorage.getItem('authToken');
  }
}
