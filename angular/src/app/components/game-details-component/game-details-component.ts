import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { GameService, RawgGameDetails } from '../../service/game/game-service';
import { Observable, switchMap, tap } from 'rxjs';
import { ApiState } from '../../service/fetch/generic-fetch-service';
import { CommonModule, Location } from '@angular/common';
import { TranslateGameDetailsPipe } from '../../pipes/translate-game-details-pipe';

@Component({
  selector: 'app-game-details-component',
  imports: [CommonModule, RouterModule, TranslateGameDetailsPipe],
  templateUrl: './game-details-component.html',
  styleUrl: './game-details-component.css'
})
export class GameDetailsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private gameService = inject(GameService);
  private location = inject(Location);

  details$!: Observable<ApiState<RawgGameDetails>>;

  // Propiedades para la descripción multilingüe
  descriptionEn: string = '';
  descriptionEs: string = '';
  currentLanguage: 'en' | 'es' = 'es'; // Por defecto en español

  ngOnInit() {
    this.details$ = this.route.paramMap.pipe(
      switchMap(params => {
        const id = Number(params.get('id'));
        const detailsState = this.gameService.loadGameDetails(id);
        detailsState.load(); // Inicia la carga
        return detailsState.state$.asObservable().pipe(
          tap(state => {
            if (state.data) {
              this.parseDescription(state.data.description);
            }
          })
        );
      })
    );
  }

  goBack(): void {
    this.location.back();
  }

  setLanguage(lang: 'en' | 'es'): void {
    this.currentLanguage = lang;
  }

  private parseDescription(description: string): void {
    const separator = /<p>Español<br \/>|<\/p>\s*<p>Español<\/p>/i;
    const parts = description.split(separator);
    this.descriptionEn = parts[0] || '';
    this.descriptionEs = parts[1] || this.descriptionEn; // Si no hay español, muestra inglés
  }
}