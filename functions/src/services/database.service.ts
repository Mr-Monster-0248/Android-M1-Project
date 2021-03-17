import * as admin from 'firebase-admin';
import User from '../models/user';
import Session from '../models/session';
import Movie from '../models/movie';
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

  await ref.set({
      username: user.Username,
      sessionIds: user.SessionIds,
    });
}

// Create new User in DB
export async function createUserInDB(newUserData: { id: string, username: string }) {
  await saveUserInDB(new User(newUserData.id, newUserData.username), true);
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
    data.username
  );
}

// Get User object from User ID
export async function findUserById(userId: string): Promise<User | null> {
  const ref = await getRefById('users', userId);
  
  if (!await checkDocExists(ref)) return null;

  const data = (await ref.get()).data();

  if (data === undefined) return null;

  return new User(
    data.id,
    data.username
  );
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
async function saveSessionInDB(session: Session, newSession: boolean = false) {
  const ref = await getRefById('sessions', session.Id);

  if (newSession && !(await checkDocExists(ref))) return;

  await ref.set({
      name: session.Name,
      genres: session.Genres,
      movies: session.Movies.map((m: Movie) => { m.Title, m.PosterUrl }),
      users: session.Users
    });
}

// Create new Session in DB
export async function createSessionInDB(session: Session) {
  await saveSessionInDB(session, true);
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
function sessionFromSnapshot(data: FirebaseFirestore.DocumentData): Session {
  return new Session(
    data.id,
    data.name,
    data.genres,
    data.movies.map((m: { title: string, posterUrl: string }) => new Movie(m.title, m.posterUrl)),
    data.users
  );
}

// Get Session object from Session ID
export async function findSessionById(sessionId: string): Promise<Session | null> {
  const ref = await getRefById('sessions', sessionId);
  
  if (!await checkDocExists(ref)) return null;

  const data = (await ref.get()).data();

  if (data === undefined) return null;

  return new Session(
    data.id,
    data.name,
    data.genres,
    data.movies.map((m: Movie) => new Movie(m.Title, m.PosterUrl)),
    data.users.map((u: { id: string, username: string }) => { u.id, u.username })
  );
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
