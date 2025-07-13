import { isPlatformBrowser } from '@angular/common';
import { computed, effect, Inject, Injectable, PLATFORM_ID, signal } from '@angular/core';

export type Theme = 'light' | 'dark' | 'system';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  // Señal para el tema actual
  theme = signal<Theme>('system');
  
  // Señal privada para rastrear la preferencia del sistema operativo
  private systemThemeIsDark = signal(false);

  // Señal pública y computada que determina si el modo oscuro debe estar activo
  public isDark = computed(() => {
    const currentTheme = this.theme();
    if (currentTheme === 'system') {
      return this.systemThemeIsDark();
    }
    return currentTheme === 'dark';
  });

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      // Inicializar el tema del sistema
      this.initializeSystemTheme();
      
      // Leer el tema inicial desde localStorage
      const savedTheme = localStorage.getItem('theme') as Theme;
      if (savedTheme && ['light', 'dark', 'system'].includes(savedTheme)) {
        this.theme.set(savedTheme);
      }

      // Aplicar el tema inicial
      this.applyTheme();

      // Efecto para guardar en localStorage y aplicar tema cuando cambie
      effect(() => {
        const currentTheme = this.theme();
        localStorage.setItem('theme', currentTheme);
        this.applyTheme();
      });

      // Efecto para aplicar tema cuando cambie la preferencia del sistema
      effect(() => {
        if (this.theme() === 'system') {
          this.applyTheme();
        }
      });
    }
  }

  private initializeSystemTheme(): void {
    if (isPlatformBrowser(this.platformId)) {
      // Detectar preferencia inicial del sistema
      const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
      this.systemThemeIsDark.set(mediaQuery.matches);
      
      // Escuchar cambios en la preferencia del sistema
      mediaQuery.addEventListener('change', (e) => {
        this.systemThemeIsDark.set(e.matches);
      });
    }
  }

  private applyTheme(): void {
    if (isPlatformBrowser(this.platformId)) {
      const htmlElement = document.documentElement;
      const shouldBeDark = this.isDark();
      
      if (shouldBeDark) {
        htmlElement.classList.add('dark');
      } else {
        htmlElement.classList.remove('dark');
      }
    }
  }

  public changeTheme(): void {
    const current = this.theme();
    let newTheme: Theme;
    
    switch (current) {
      case 'light':
        newTheme = 'dark';
        break;
      case 'dark':
        newTheme = 'system';
        break;
      default:
        newTheme = 'light';
        break;
    }
    
    this.theme.set(newTheme);
  }

  // Método para obtener el tema actual
  public getCurrentTheme(): Theme {
    return this.theme();
  }

  // Método para establecer un tema específico
  public setTheme(theme: Theme): void {
    this.theme.set(theme);
  }
}