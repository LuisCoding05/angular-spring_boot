import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth/auth-service';


/**
 * Interceptor funcional que añade el token JWT a las cabeceras de las peticiones salientes.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const authToken = authService.getToken();

  if (authToken) {
    // Clona la petición y añade la cabecera de autorización.
    const authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${authToken}` }
    });
    return next(authReq);
  }

  // Si no hay token, la petición continúa sin modificarse.
  return next(req);
};


