class Movie {
  private title: string;
  private posterUrl: string;


  constructor(
    title: string,
    posterUrl: string
  ) {
    this.title = title;
    this.posterUrl = posterUrl;
  }


  get Title(): string {
    return this.title;
  }

  get PosterUrl(): string {
    return this.posterUrl;
  }
}


export default Movie;
