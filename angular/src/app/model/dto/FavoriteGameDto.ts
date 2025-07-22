export interface FavoriteGameDto {
  id: number;
  rawgId: number;
  title: string;
  releaseDate: string;
  backgroundImage: string;
  rawgRating: number;
  personalReview?: string;
  personalRating?: number;
  platforms: string[];
}
