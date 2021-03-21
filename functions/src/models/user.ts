import Session from './session';

class User {
  constructor(
    private id: string,
    private username: string = '',
    private sessionIds: string[] = [],
    private done: boolean = false
  ) {
    this.id = id;
    this.username = username === ''
      ? id          // Custom random IDs ??
      : username;
    this.sessionIds = sessionIds;
    this.done = done;
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
    return this.sessionIds;
  }

  get Done(): boolean {
    return this.done;
  }

  set Done(done: boolean) {
    this.done = done;
  }



  isDone(): boolean {
    return this.Done;
  }



  addSession(sessionId: string) {
    if (!this.hasSession(sessionId)) this.SessionIds.push(sessionId);
  }

  removeSession(session: Session) {
    if (this.isOwnerOfSession(session)) session.removeOwnerAndPickNewOwner();

    if (this.hasSession(session.Id))
      this.SessionIds.splice(this.SessionIds.indexOf(session.Id));
  }

  isOwnerOfSession(session: Session) {
    return session.OwnerId === this.Id;
  }

  hasSession(sessionId: string) {
    return this.SessionIds.includes(sessionId);
  }
}


export default User;
