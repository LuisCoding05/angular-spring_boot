import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'translateGameDetails',
  standalone: true,
})
export class TranslateGameDetailsPipe implements PipeTransform {
  private translations: { [key: string]: string } = {
    exceptional: 'Excepcional',
    recommended: 'Recomendado',
    meh: 'Meh',
    skip: 'Saltar',

    // Labels de la sección de detalles
  };

  transform(value: string): string {
    if (!value) {
      return '';
    }
    // Devuelve la traducción si existe, o el valor original si no.
    return this.translations[value.toLowerCase()] || value;
  }
}
