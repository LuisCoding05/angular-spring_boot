import { Component, inject } from '@angular/core';
import { GenericFetchService } from '../../service/fetch/generic-fetch-service';

import { CommonModule } from '@angular/common';
import { UserDto } from '../../model/dto/UserDto';
import { AppConstants } from '../../const/app-constants';

@Component({
  selector: 'app-dashboard-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard-component.html',
  styleUrl: './dashboard-component.css'
})
export class DashboardComponent {
  private genericFetchService = inject(GenericFetchService);

  // Estado reactivo para la petición al dashboard (ahora devuelve user y favoriteGames)
  private dashboardApi = this.genericFetchService.createApiState<any>(AppConstants.API_BASE_URL + '/api/dashboard/index');

  dashboardState$ = this.dashboardApi.state$;
}
