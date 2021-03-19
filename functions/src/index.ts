import * as functions from 'firebase-functions';
import { SessionState } from './models/session';
import User from './models/user';
import {
  createUserInDB,
  deleteUserFromDB,
  updateUserInDB,
  findAllSessionsForUser,
  findUserById,
  updateSessionInDB,
  findSessionById,
  sessionFromSnapshot
} from './services/database.service';
import { getMoviesFromSearchParams } from './services/movies.service';
import { checkSameArray } from './utils/misc.util';




// Handle User creation in auth
export const onUserCreate = functions.auth.user().onCreate(async (user) => {
  await createUserInDB(user.uid);
});

// Handle User username update
export const updateUserUsername = functions.https.onCall(async (data: User) => {
  await updateUserInDB(data);

  const sessions = await findAllSessionsForUser(data);
  if (sessions === null) return;

  for (const session of sessions) {
    for (const user of session.Users) {
      if (user.id === data.Id) {
        user.username = data.Username;
        await updateSessionInDB(session);
      }
    }
  }
});

// Handle User deletion in auth
export const onUserDelete = functions.auth.user().onDelete(async (user) => {
  
  const search = await findUserById(user.uid);
  if (search === null) return;

  await deleteUserFromDB(search);

  const sessions = await findAllSessionsForUser(search);
  if (sessions === null) return;

  for (const session of sessions) {
    for (const user of session.Users) {
      if (user.id === search.Id) {
        user.username = search.Username;
        await updateSessionInDB(session);
      }
    }
  }
});



// Handle Session creation in store
export const onSessionCreate = functions.firestore.document('sessions/{id}').onCreate(async (session) => {
  const ses = await findSessionById(session.id);
  if (ses === null) return;

  const movieIds = await getMoviesFromSearchParams(ses.SearchParams);

  for (const movieId of movieIds) {
    ses.addMovie(movieId);
  }

  ses.State = SessionState.READY;
  
  await updateSessionInDB(ses);
});

// Handle Session update in store
export const onSessionUpdate = functions.firestore.document('sessions/{id}').onUpdate(async (session) => {
  
  const before = sessionFromSnapshot(session.before.data());
  const after = sessionFromSnapshot(session.after.data());

  
  if (!checkSameArray(before.SearchParams.Query.genreIds, after.SearchParams.Query.genreIds)) {
    const movieIds = await getMoviesFromSearchParams(after.SearchParams);

    for (const movieId of movieIds) {
      after.addMovie(movieId);
    }
  }


  if (before.OwnerId !== after.OwnerId) {
    // TODO
  }


  after.State = SessionState.READY;

  await updateSessionInDB(after);
});

// Handle User addition to Session
export const addUserToSession = functions.https.onCall(async (data: { userId: string, sessionId: string }) => {
  const user = await findUserById(data.userId);
  const session = await findSessionById(data.sessionId);

  if (session === null || user === null) return;

  if (!session.hasUser(user.Id)) session.addUser({ id: user.Id, username: user.Username });
  await updateSessionInDB(session);

  if (!user.hasSession(session.Id)) user.addSession(session.Id);
  await updateUserInDB(user);
});

// Handle User deletion from Session
export const removeUserFromSession = functions.https.onCall(async (data: { userId: string, sessionId: string }) => {
  const user = await findUserById(data.userId);
  const session = await findSessionById(data.sessionId);

  if (session === null || user === null) return;

  if (session.hasUser(user.Id)) session.removeUserById(data.userId);
  await updateSessionInDB(session);

  if (!user.hasSession(session.Id)) user.removeSession(session);
  await updateUserInDB(user);
});
