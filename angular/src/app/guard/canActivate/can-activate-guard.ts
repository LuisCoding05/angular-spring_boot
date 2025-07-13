import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';

@Injectable({
  providedIn: 'root'
})
export class CanActivateGuard implements CanActivate {
  private authService = inject(AuthService);
  private router = inject(Router);
  constructor() { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean | UrlTree {
    
    if (this.authService.isAuthenticated()) {
      return true;
    }
    
    // Redirige a login con URL de retorno
    return this.router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url }
    });
  }
}





