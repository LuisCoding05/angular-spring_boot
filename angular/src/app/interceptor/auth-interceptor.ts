import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth/auth-service';
import { AppConstants } from '../const/app-constants';
import { catchError, throwError } from 'rxjs';


/**
 * Interceptor funcional que añade el token JWT a las cabeceras de las peticiones salientes.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  const isPublic = AppConstants.API_PUBLIC_ENDPOINTS.some(endpoint => req.url.startsWith(endpoint));

  let request = req;
  if (authToken && (!isPublic || req.url.includes("add-to-favorites"))) {
    request = req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
  }

  return next(request).pipe(
    catchError((error) => {
      if (error.status === 401) {
        // Por ejemplo, redirigir al login
        authService.logout();
        // O puedes limpiar el token, mostrar un mensaje, etc.
      }
      return throwError(() => error);
    })
  );
};


