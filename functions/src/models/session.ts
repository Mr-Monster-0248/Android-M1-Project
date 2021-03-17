import Movie from "./movie";


class Session {
  private id: string;
  private name: string;
  private genres: string[];
  private movies: Movie[];
  private users: { id: string, username: string }[];


  constructor(
    id: string,
    name: string,
    genres: string[],
    movies: Movie[] = [],
    users: { id: string, username: string }[] = []
  ) {
    this.id = id;
    this.name = name;
    this.genres = genres;
    this.movies = movies;
    this.users = users;
  }


  get Id(): string {
    return this.Id;
  }

  get Name(): string {
    return this.name;
  }

  get Genres(): string[] {
    return this.genres;
  }

  set Genres(genres: string[]) {
    this.genres = genres;
  }

  get Movies(): Movie[] {
    return this.movies;
  }

  set Movies(movies: Movie[]) {
    this.movies = movies;
  }

  get Users(): { id: string, username: string }[] {
    return this.users;
  }


  addUser(user: { id: string, username: string }) {
    this.Users.push(user);
  }

  removeUserById(userId: string) {
    const search = this.Users.find(u => u.id === userId);
    if (search !== undefined) this.Users.splice(this.Users.indexOf(search));
  }


  addMovie(movie: Movie) {
    if (!this.Movies.includes(movie))
      this.Movies.push(movie);
  }

  removeMovie(movie: Movie) {
    if (this.Movies.includes(movie))
      this.Movies.splice(this.Movies.indexOf(movie));
  }
}


export default Session;
