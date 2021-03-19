import { getRandomValueInArray } from "../utils/misc.util";
import SearchParams from "./search-params";

export enum SessionState {
  NEW = 0,
  READY = 1,
  VOTING = 2,
  DONE = 3
}

class Session {
  constructor(
    private id: string,
    private ownerId: string,
    private name: string,
    private searchParams: SearchParams,
    private movies: { id: string, score: number }[] = [],
    private users: { id: string, username: string }[] = [],
    private state: SessionState
  ) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.searchParams = searchParams;
    this.movies = movies;
    this.users = users;
    this.state = state;
  }


  get Id(): string {
    return this.Id;
  }

  get OwnerId(): string {
    return this.ownerId;
  }

  set OwnerId(o: string) {
    this.ownerId = o;
  }

  get Name(): string {
    return this.name;
  }

  get SearchParams(): SearchParams {
    return this.searchParams;
  }

  set SearchParams(searchParams: SearchParams) {
    this.searchParams = searchParams;
  }

  get Movies(): { id: string, score: number }[] {
    return this.movies;
  }

  set Movies(movies: { id: string, score: number }[]) {
    this.movies = movies;
  }

  get Users(): { id: string, username: string }[] {
    return this.users;
  }

  get State(): SessionState {
    return this.state;
  }

  set State(state: SessionState) {
    this.state = state;
  }


  removeOwnerAndPickNewOwner() {
    let newOwnerId = this.OwnerId;

    do {
      newOwnerId = getRandomValueInArray(this.Users).id;
    } while (newOwnerId === this.OwnerId);

    this.removeUserById(this.OwnerId);

    this.OwnerId = newOwnerId;
  }


  addUser(user: { id: string, username: string }) {
    this.Users.push(user);
  }

  hasUser(userId: string) {
    return this.Users.find(u => u.id === userId);
  }

  removeUserById(userId: string) {
    const search = this.Users.find(u => u.id === userId);
    if (search !== undefined) this.Users.splice(this.Users.indexOf(search));
  }


  addMovie(movie: { id: string, score: number }) {
    if (!this.Movies.find(movie => movie.id === movie.id))
      this.Movies.push(movie);
  }

  removeMovie(movieId: string) {
    const search = this.Movies.find((movie) => movie.id === movieId);
    if (search !== undefined) this.Movies.splice(this.Movies.indexOf(search));
  }

  getWinningMovieId(): string {
    const highscore = Math.max(...this.Movies.map(m => m.score));
    const winners = this.Movies.filter(movie => movie.score === highscore);

    return getRandomValueInArray(winners).id;
  }


  isNew(): boolean {
    return this.State === SessionState.NEW;
  }

  isReady(): boolean {
    return this.State === SessionState.READY;
  }

  isVoting(): boolean {
    return this.State === SessionState.VOTING;
  }

  isDone(): boolean {
    return this.State === SessionState.DONE;
  }
}


export default Session;
