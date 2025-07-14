import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar-component/navbar-component';
import { ThemeService } from './service/theme/theme-service';
import { FooterComponent } from "./components/footer-component/footer-component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent, FooterComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {

  public themeService = inject(ThemeService);
  protected title = 'angular';

  // El constructor ya no necesita hacer nada relacionado con el tema.
  constructor() {
  }
}
