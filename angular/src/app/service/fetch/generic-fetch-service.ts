import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, catchError, of, finalize } from 'rxjs';

export interface ApiState<T> {
  data: T | null;
  loading: boolean;
  error: string | null;
  errorObj?: any;
}

export interface PostApiState<T> extends ApiState<T> {
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class GenericFetchService {
  private http = inject(HttpClient);

  /**
   * Crea un estado reactivo para cualquier endpoint GET con limitador de peticiones
   * @param url - URL del endpoint
   * @param autoLoad - Si debe cargar automáticamente (default: true)
   * @param rateLimit - Opcional: { maxRequests: number, perMilliseconds: number }
   * @returns Un BehaviorSubject con el estado de la petición
   */
  createApiState<T>(url: string, autoLoad: boolean = true, rateLimit?: { maxRequests: number, perMilliseconds: number }): {
    state$: BehaviorSubject<ApiState<T>>;
    load: () => void;
    retry: () => void;
  } {
    const initialState: ApiState<T> = {
      data: null,
      loading: false,
      error: null
    };
    const state$ = new BehaviorSubject<ApiState<T>>(initialState);
    // --- Rate limiter ---
    let requestTimestamps: number[] = [];
    const isRateLimited = () => {
      if (!rateLimit) return false;
      const now = Date.now();
      // Elimina timestamps fuera del intervalo
      requestTimestamps = requestTimestamps.filter(ts => now - ts < rateLimit.perMilliseconds);
      return requestTimestamps.length >= rateLimit.maxRequests;
    };
    const load = () => {
      if (isRateLimited()) {
        state$.next({
          ...state$.value,
          loading: false,
          error: `Has superado el límite de ${rateLimit?.maxRequests} peticiones cada ${Math.round((rateLimit?.perMilliseconds || 1000)/1000)} segundos.`
        });
        return;
      }
      requestTimestamps.push(Date.now());
      state$.next({ ...state$.value, loading: true, error: null });
      this.http.get<T>(url).pipe(
        catchError(error => {
          console.error(`Error fetching ${url}:`, error);
          let errorObj = null;
          // Si el backend devuelve un objeto de error, lo guardamos
          if (error?.error && typeof error.error === 'object') {
            errorObj = error.error;
          }
          state$.next({ 
            data: null, 
            loading: false, 
            error: error.message || 'Error al cargar datos',
            errorObj
          });
          return of(null);
        }),
        finalize(() => {
          if (state$.value.loading) {
            state$.next({ ...state$.value, loading: false });
          }
        })
      ).subscribe(data => {
        if (data) {
          state$.next({ data, loading: false, error: null, errorObj: null });
        }
      });
    };
    const retry = () => {
      console.log('🔄 Reintentando...');
      load();
    };
    if (autoLoad) {
      load();
    }
    return { state$, load, retry };
  }

  /**
   * Crea un estado reactivo para operaciones POST
   * @param url - URL del endpoint
   * @returns Un BehaviorSubject con el estado de la petición POST
   */
  createPostApiState<TRequest, TResponse>(url: string): {
    state$: BehaviorSubject<PostApiState<TResponse>>;
    post: (data: TRequest) => void;
    reset: () => void;
  } {
    const initialState: PostApiState<TResponse> = {
      data: null,
      loading: false,
      error: null,
      success: false
    };
    
    const state$ = new BehaviorSubject<PostApiState<TResponse>>(initialState);
    
    const post = (data: TRequest) => {
      state$.next({ ...state$.value, loading: true, error: null, success: false });
      
      this.http.post<TResponse>(url, data).pipe(
        catchError(error => {
          console.error(`Error posting to ${url}:`, error);
          state$.next({ 
            data: null, 
            loading: false, 
            error: error.message || 'Error al enviar datos',
            success: false 
          });
          return of(null);
        }),
        finalize(() => {
          if (state$.value.loading) {
            state$.next({ ...state$.value, loading: false });
          }
        })
      ).subscribe(response => {
        if (response) {
          state$.next({ 
            data: response, 
            loading: false, 
            error: null, 
            success: true 
          });
        }
      });
    };
    
    const reset = () => {
      state$.next(initialState);
    };
    
    return { state$, post, reset };
  }

  /**
   * Crea un estado reactivo para operaciones PUT
   * @param url - URL del endpoint
   * @returns Un BehaviorSubject con el estado de la petición PUT
   */
  createPutApiState<TRequest, TResponse>(url: string): {
    state$: BehaviorSubject<PostApiState<TResponse>>;
    put: (data: TRequest) => void;
    reset: () => void;
  } {
    const initialState: PostApiState<TResponse> = {
      data: null,
      loading: false,
      error: null,
      success: false
    };
    
    const state$ = new BehaviorSubject<PostApiState<TResponse>>(initialState);
    
    const put = (data: TRequest) => {
      state$.next({ ...state$.value, loading: true, error: null, success: false });
      
      this.http.put<TResponse>(url, data).pipe(
        catchError(error => {
          console.error(`Error putting to ${url}:`, error);
          state$.next({ 
            data: null, 
            loading: false, 
            error: error.message || 'Error al actualizar datos',
            success: false 
          });
          return of(null);
        }),
        finalize(() => {
          if (state$.value.loading) {
            state$.next({ ...state$.value, loading: false });
          }
        })
      ).subscribe(response => {
        if (response) {
          state$.next({ 
            data: response, 
            loading: false, 
            error: null, 
            success: true 
          });
        }
      });
    };
    
    const reset = () => {
      state$.next(initialState);
    };
    
    return { state$, put, reset };
  }

  /**
   * Crea un estado reactivo para operaciones DELETE
   * @param url - URL del endpoint
   * @returns Un BehaviorSubject con el estado de la petición DELETE
   */
  createDeleteApiState<TResponse = any>(url: string): {
    state$: BehaviorSubject<PostApiState<TResponse>>;
    delete: (id?: string | number) => void;
    reset: () => void;
  } {
    const initialState: PostApiState<TResponse> = {
      data: null,
      loading: false,
      error: null,
      success: false
    };
    
    const state$ = new BehaviorSubject<PostApiState<TResponse>>(initialState);
    
    const deleteItem = (id?: string | number) => {
      state$.next({ ...state$.value, loading: true, error: null, success: false });
      
      const deleteUrl = id ? `${url}/${id}` : url;
      
      this.http.delete<TResponse>(deleteUrl).pipe(
        catchError(error => {
          console.error(`Error deleting from ${deleteUrl}:`, error);
          state$.next({ 
            data: null, 
            loading: false, 
            error: error.message || 'Error al eliminar datos',
            success: false 
          });
          return of(null);
        }),
        finalize(() => {
          if (state$.value.loading) {
            state$.next({ ...state$.value, loading: false });
          }
        })
      ).subscribe(response => {
        state$.next({ 
          data: response, 
          loading: false, 
          error: null, 
          success: true 
        });
      });
    };
    
    const reset = () => {
      state$.next(initialState);
    };
    
    return { state$, delete: deleteItem, reset };
  }

  /**
   * Método simple para POST único (no reactivo)
   * @param url - URL del endpoint
   * @param data - Datos a enviar
   * @returns Promise con la respuesta
   */
  async simplePost<TRequest, TResponse>(url: string, data: TRequest): Promise<TResponse | null> {
    try {
      const response = await this.http.post<TResponse>(url, data).toPromise();
      return response || null;
    } catch (error) {
      console.error(`Error posting to ${url}:`, error);
      return null;
    }
  }

  /**
   * Método simple para fetch único (no reactivo)
   * @param url - URL del endpoint
   * @returns Promise con los datos
   */
  async simpleFetch<T>(url: string): Promise<T | null> {
    try {
      const data = await this.http.get<T>(url).toPromise();
      return data || null;
    } catch (error) {
      console.error(`Error fetching ${url}:`, error);
      return null;
    }
  }
}