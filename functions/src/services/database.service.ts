import * as admin from 'firebase-admin';
import User from '../models/user';
import Session from '../models/session';
import SearchParams from '../models/search-params';
import { DocumentData } from '@firebase/firestore-types';



admin.initializeApp();
const db = admin.firestore();


// Check whether document exists
async function checkDocExists(ref: FirebaseFirestore.DocumentReference<DocumentData>): Promise<boolean> {
  return (await ref.get()).exists;
}

// Get document reference from DB by Id
async function getRefById(collectionName: string, docId: string): Promise<FirebaseFirestore.DocumentReference<DocumentData>> {
  return await db.collection(collectionName).doc(docId);
}




// Save User in DB
async function saveUserInDB(user: User, newUser: boolean = false) {
  const ref = await getRefById('users', user.Id);

  if (newUser && !(await checkDocExists(ref))) return;

  if (newUser) await ref.set({ id: user.Id });

  await ref.set({
      username: user.Username,
      sessionIds: user.SessionIds,
    });
}

// Create new User in DB
export async function createUserInDB(id: string) {
  await saveUserInDB(new User(id), true);
}

// Update existing User in DB
export async function updateUserInDB(user: User) {
  await saveUserInDB(user);
}

// Delete User from DB
export async function deleteUserFromDB(user: User) {
  await (await getRefById('users', user.Id)).delete();
}

// User from snapshot
function userFromSnapshot(data: FirebaseFirestore.DocumentData): User {
  return new User(
    data.id,
    data.username,
    data.sessionIds
  );
}

// Get User object from User ID
export async function findUserById(userId: string): Promise<User | null> {
  const ref = await getRefById('users', userId);
  
  if (!await checkDocExists(ref)) return null;

  const data = (await ref.get()).data();

  return data === undefined ? null : userFromSnapshot(data);
}

// Find all Sessions for a User
export async function findAllSessionsForUser(user: User): Promise<Session[] | null> {
  const userData = { id: user.Id, username: user.Username };

  const snapshot = await db
    .collection('users')
    .where('users', 'array-contains', userData)
    .get();
  
  if (snapshot.empty) return null;

  const sessions: Session[] = [];

  snapshot.forEach((doc) => {
    const data = doc.data();
    sessions.push(sessionFromSnapshot(data));
  });

  return sessions;
}



// Save Session in DB
async function saveSessionInDB(session: Session) {
  const ref = await getRefById('sessions', session.Id);

  if (session.isNew() && !(await checkDocExists(ref))) return;

  if (session.isNew()) await ref.set({ id: session.Id });

  await ref.set({
    ownerId: session.OwnerId,
    name: session.Name,
    searchParams: { query: session.SearchParams.Query, max_nbr: session.SearchParams.Max_nbr },
    movies: session.Movies,
    users: session.Users,
    state: session.State
  });
}

// Create new Session in DB
export async function createSessionInDB(session: Session) {
  await saveSessionInDB(session);
}

// Update existing Session in DB
export async function updateSessionInDB(session: Session) {
  await saveSessionInDB(session);
}

// Delete Session from DB
export async function deleteSessionFromDB(session: Session) {
  await (await getRefById('sessions', session.Id)).delete();
}

// Session from snapshot
export function sessionFromSnapshot(data: FirebaseFirestore.DocumentData): Session {
  return new Session(
    data.id,
    data.ownerId,
    data.name,
    new SearchParams(data.searchParams.query, data.searchParams.max_nbr),
    data.movies,
    data.users.map((u: { id: string, username: string }) => { u.id, u.username }),
    data.state
  );
}

// Get Session object from Session ID
export async function findSessionById(sessionId: string): Promise<Session | null> {
  const ref = await getRefById('sessions', sessionId);
  
  if (!await checkDocExists(ref)) return null;

  const data = (await ref.get()).data();

  return data === undefined ? null : sessionFromSnapshot(data);
}

// Find all Users for a Session
export async function findAllUsersForSession(session: Session): Promise<User[] | null> {
  const snapshot = await db
    .collection('users')
    .where('sessionIds', 'array-contains', session.Id)
    .get();
  
  if (snapshot.empty) return null;

  const users: User[] = [];

  snapshot.forEach((doc) => {
    const data = doc.data();
    users.push(userFromSnapshot(data));
  })

  return users;
}
