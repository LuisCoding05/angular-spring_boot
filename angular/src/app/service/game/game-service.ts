import { Injectable, inject } from '@angular/core';
import { GenericFetchService } from '../fetch/generic-fetch-service';
import { BehaviorSubject } from 'rxjs';
import { AppConstants } from '../../const/app-constants';

export interface RawgGame {
  id: number;
  slug: string;
  name: string;
  released: string;
  rating: number;
  metacritic: number;
  background_image: string;
  platforms: any[];
  genres: any[];
  stores: any[];
}

export interface RawgResponse {
  count: number;
  next: string;
  previous: string;
  content: RawgGame[];
}

@Injectable({ providedIn: 'root' })
export class GameService {
  private fetchService = inject(GenericFetchService);
  private apiUrl = '/api/games';

  /**
   * Estado reactivo para la lista de juegos RAWG con limitador de peticiones
   * Máximo 5 peticiones cada 10 segundos
   */
  gamesState = this.fetchService.createApiState<RawgResponse>(
    `${AppConstants.API_BASE_URL}${this.apiUrl}?page=1&page_size=4`,
    true,
    { maxRequests: 5, perMilliseconds: 10000 }
  );

  /**
   * Recarga los juegos (puedes pasar search, page, pageSize)
   * Usa el limitador de peticiones
   */
  loadGames(params?: { search?: string; page?: number; pageSize?: number }) {

    try {
      
    } catch (error) {
      
    }

    let url = `${AppConstants.API_BASE_URL}${this.apiUrl}`;
    const page = params?.page ?? 1;
    const pageSize = params?.pageSize ?? 4;
    const search = params?.search ?? '';
    url += `?page=${page}&page_size=${pageSize}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    // Actualiza el estado usando el método load del createApiState
    this.gamesState = this.fetchService.createApiState<RawgResponse>(
      url,
      false,
      { maxRequests: 2, perMilliseconds: 5000 }
    );
    try {
      this.gamesState.load();
    } catch (error: any) {
      this.gamesState.state$.next({
        data: null,
        loading: false,
        error: error?.message || 'Error al cargar juegos'
      });
    }
  }
}
