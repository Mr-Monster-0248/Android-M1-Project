import Session from './session';

class User {
  constructor(
    private id: string,
    private username: string = '',
    private sessionIds: string[] = []
  ) {
    this.id = id;
    this.username = username;
    this.sessionIds = sessionIds;
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


  addSession(sessionId: string) {
    this.SessionIds.push(sessionId);
  }

  removeSession(session: Session) {
    if (this.isOwnerOfSession(session)) session.removeOwnerAndPickNewOwner();

    if (this.SessionIds.includes(session.Id))
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
