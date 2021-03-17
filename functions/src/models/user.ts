import Session from './session';

class User {
  private id: string;
  private username: string;
  private sessionIds: string[];


  constructor(
    id: string,
    username: string,
  ) {
    this.id = id;
    this.username = username;
    this.sessionIds = [];
  }


  get Id(): string {
    return this.id;
  }

  get Username(): string {
    return this.username;
  }

  set Username(u: string) {
    this.username = u;
  }

  get SessionIds(): string[] {
    return this.SessionIds;
  }


  addSession(session: Session) {
    this.SessionIds.push(session.Id);
  }

  removeSessionById(sessionId: string) {
    if (this.SessionIds.includes(sessionId))
      this.SessionIds.splice(this.SessionIds.indexOf(sessionId));
  }
}


export default User;
