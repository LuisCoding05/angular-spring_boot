import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { GameService } from '../../service/game/game-service';
import { RawgGame } from '../../service/game/game-service';
import { BehaviorSubject } from 'rxjs';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth/auth-service';

@Component({
  selector: 'app-home-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './home-component.html',
  styleUrl: './home-component.css'
})
export class HomeComponent implements OnInit {
  private gameService = inject(GameService);
  games$ = this.gameService.gamesState.state$.asObservable();
  addFavoriteState$ = this.gameService.addFavoriteState.state$.asObservable();
  authService = inject(AuthService);

  ngOnInit() {
    this.gameService.loadGames();
    this.games$.subscribe(state => {
      if (state.error && state.errorObj) {
        console.log('Error recibido del backend:', state.errorObj);
      }
      if (state.data) {
        console.log('Respuesta exitosa del backend:', state.data);
      }
    });
  }

  addToFavorites(game: any) {
    this.gameService.addGameToFavorites(game);
  }
}
