import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth/auth-service';
import { AppConstants } from '../const/app-constants';


/**
 * Interceptor funcional que añade el token JWT a las cabeceras de las peticiones salientes.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  const isPublic = AppConstants.API_PUBLIC_ENDPOINTS.some(endpoint => req.url.startsWith(endpoint));

  if (authToken && (!isPublic || req.url.includes("add-to-favorites"))) {
    console.log(`Añadiendo token ${authToken} a la petición:`, req.url);
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
    return next(cloned);
  }

  // Si no hay token, la petición continúa sin modificarse.
  return next(req);
};


