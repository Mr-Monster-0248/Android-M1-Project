// TODO: take care of SearchParams in Session object

class Session {
  constructor(
    private id: string,
    private ownerId: string,
    private name: string,
    private genres: string[],
    private movieIds: string[] = [],
    private users: { id: string, username: string }[] = []
  ) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    this.genres = genres;
    this.movieIds = movieIds;
    this.users = users;
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

  get Genres(): string[] {
    return this.genres;
  }

  set Genres(genres: string[]) {
    this.genres = genres;
  }

  get MovieIds(): string[] {
    return this.movieIds;
  }

  set MovieIds(movieIds: string[]) {
    this.movieIds = movieIds;
  }

  get Users(): { id: string, username: string }[] {
    return this.users;
  }


  removeOwnerAndPickNewOwner() {
    let newOwnerId = this.OwnerId;

    do {
      newOwnerId = this.Users[Math.floor(Math.random() * this.Users.length)].id;
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


  addMovie(movieId: string) {
    if (!this.MovieIds.includes(movieId))
      this.MovieIds.push(movieId);
  }

  removeMovie(movieId: string) {
    if (this.MovieIds.includes(movieId))
      this.MovieIds.splice(this.MovieIds.indexOf(movieId));
  }
}


export default Session;
