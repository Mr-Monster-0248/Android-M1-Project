import * as functions from 'firebase-functions';
import Session from './models/session';
import User from './models/user';
import {
  createUserInDB,
  deleteUserFromDB,
  updateUserInDB,
  deleteSessionFromDB
} from './services/database.service';



// Handle User creation
export const createNewUser = functions.https.onCall(async (data: { id: string, username: string }) => {
  await createUserInDB(data);
});

// Handle User update
export const updateUser = functions.https.onCall(async (data: { user: User }) => {
  await updateUserInDB(data.user);

  // 2. Find all Sessions with this User in them
  // 3. Update all Sessions found
});

// Handle User deletion
export const deleteUser = functions.https.onCall(async (data: { user: User }) => {
  await deleteUserFromDB(data.user);

  // 2. Find all Sessions with this User in them
  // 3. Update all Sessions found
});



// Handle Session creation
export const createNewSession = functions.https.onCall(async (data: { id: string, name: string, genres: string[] }) => {
  // 1. Query API pour liste de films
  // 2. Process films -> dans la Session
  // 3. Ajouter la Session dans DB
});

// Handle Session update
export const updateSession = functions.https.onCall(async (data: { session: Session }) => {
  // 1. Check changes
  // 2. Update Session
});


// Handle Session deletion
export const deleteSession = functions.https.onCall(async (data: { session: Session }) => {
  await deleteSessionFromDB(data.session);
  // 2. Find all Users with this Session in them
  // 3. Update all Users found
});

