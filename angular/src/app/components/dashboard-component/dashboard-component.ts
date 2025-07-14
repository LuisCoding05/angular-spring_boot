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

  // Creamos un estado reactivo para la petición al dashboard.
  // La petición se lanzará automáticamente gracias a `autoLoad: true` (valor por defecto).
  // El interceptor se encargará de añadir el token.
  private userApi = this.genericFetchService.createApiState<UserDto>(AppConstants.API_BASE_URL + '/api/dashboard/index');
  
  // Exponemos el estado al template para poder usarlo con la pipe `async`.
  userState$ = this.userApi.state$;
}
