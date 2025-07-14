export class AppConstants {
  static readonly API_BASE_URL = 'http://localhost:8080';
static readonly DEFAULT_AVATAR = "https://res.cloudinary.com/dlgpvjulu/image/upload/v1744483544/default_bumnyb.webp";
  static readonly MAX_REVIEW_LENGTH = 1000;

  static readonly GENRES = [
    'Acción', 'Aventura', 'Drama', 'Comedia', 'Documental', 'Fantasía'
  ];

  static readonly FITNESS_TYPES = {
    cardio: 'Cardio',
    strength: 'Fuerza',
    yoga: 'Yoga',
    hiit: 'HIIT'
  };
}
