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
    private users: { id: string, username: string, done: boolean }[] = [],
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
    return this.id;
  }

  set Id(id: string) {
    this.id = id;
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

  get Users(): { id: string, username: string, done: boolean }[] {
    return this.users;
  }

  get State(): SessionState {
    return this.state;
  }

  set State(state: SessionState) {
    this.state = state;
  }


  pickNewOwner() {
    this.OwnerId = getRandomValueInArray(this.Users).id;
  }


  addUser(user: { id: string, username: string, done: boolean }) {
    if (!this.hasUser(user.id)) this.Users.push(user);
    else {
      const search = this.Users.find(u => u.id === user.id);
      if (search !== undefined) {
        if (search.username !== user.username)
          this.Users[this.Users.indexOf(search)].username = user.username;
        
        if (search.done !== user.done)
          this.Users[this.Users.indexOf(search)].done = user.done;
      } 
    }
  }

  hasUser(userId: string) {
    return this.Users.some(u => u.id === userId);
  }

  removeUserById(userId: string) {
    if (this.hasUser(userId))
      this.Users.splice(this.Users.findIndex(u => u.id === userId));
  }


  addMovie(movie: { id: string, score: number }) {
    if (!this.hasMovie(movie.id)) this.Movies.push(movie);
  }

  hasMovie(movieId: string) {
    return this.Movies.some(m => m.id === movieId);
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


  checkIfDone() {
    if (this.Users.every(user => user.done)) this.State = SessionState.DONE
  }


  setUserDone(userId: string) {
    if (!this.hasUser(userId)) return;
    this.Users[this.Users.findIndex(u => u.id === userId)].done = true;
  }
}


export default Session;
