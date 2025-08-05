import { isPlatformBrowser } from '@angular/common';
import { Injectable, PLATFORM_ID, inject, signal } from '@angular/core';
import { AppConstants } from '../../const/app-constants';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private platformId = inject(PLATFORM_ID);
  private router = inject(Router);
  isAuthenticated = signal(false);
  authToken = signal<string | null>(null);
  avatarUrl = signal<string | null>(null);

  constructor() {
    // Esta lógica solo debe ejecutarse en el navegador.
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('authToken');
      const avatar = localStorage.getItem('avatar');
      if (token) {
        this.isAuthenticated.set(true);
        this.authToken.set(token);
        this.avatarUrl.set(avatar);
      }
    }
  }

  isLoggedIn() {
    return this.isAuthenticated();
  }

  login(token: string, avatar = AppConstants.DEFAULT_AVATAR) {
    if (isPlatformBrowser(this.platformId)) {
      this.authToken.set(token);
      this.isAuthenticated.set(true);
      this.avatarUrl.set(avatar);
      localStorage.setItem('authToken', token);
      localStorage.setItem('avatar', avatar);
    }
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      this.authToken.set(null);
      this.isAuthenticated.set(false);
      this.avatarUrl.set(null);
      localStorage.removeItem('authToken');
      localStorage.removeItem('avatar');
      this.router.navigate(['/login']);
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      // Prioriza el token en memoria (signal) y luego recurre a localStorage.
      return this.authToken() || localStorage.getItem('authToken');
    }
    // En el servidor, no hay token.
    return null;
  }

}