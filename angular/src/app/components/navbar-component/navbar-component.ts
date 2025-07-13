import { Component, computed, inject } from '@angular/core';
import { ThemeService } from '../../service/theme/theme-service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';

@Component({
  selector: 'app-navbar-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar-component.html',
  styleUrl: './navbar-component.css'
})
export class NavbarComponent {
  isProfileMenuOpen = false;
  isMobileMenuOpen = false;
  themeService = inject(ThemeService);
  public isLightTheme = computed(() => this.themeService.theme() === 'light');
  public isDarkTheme = computed(() => this.themeService.theme() === 'dark');
  public isSystemTheme = computed(() => this.themeService.theme() === 'system');

  public authService = inject(AuthService);

  public navLinks = [
    { label: 'Inicio', link: '/' },
    { label: 'Iniciar sesión', link: '/login' },
    { label: 'Perfil', link: '/profile/index' },
    { label: 'Registrarse', link: '/register' },
  ]

  public profileLinks = [
    { label: 'Tu perfil', link: '/profile/index', id: 0 },
    { label: 'Configuración', link: '/profile/settings', id: 1 },
    { label: 'Cerrar sesión', link: '/logout', id: 2 },
  ]

  toggleProfileMenu(): void {
    this.isProfileMenuOpen = !this.isProfileMenuOpen;
  }

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  changeTheme() {
    this.themeService.changeTheme();
  }
}
