import * as functions from 'firebase-functions';
import Session, { SessionState } from './models/session';
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
import { getLocalizedMovieData, getMoviesFromSearchParams } from './services/movies.service';




// * : function needs to be tested/debugged




// Handle User creation in auth
export const onUserCreate = functions.auth.user().onCreate(async (user) => {
  await createUserInDB(user.uid);
});

// * Handle User username update
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

  const sessions = await findAllSessionsForUser(search);
  if (sessions === null) return;

  for (const session of sessions) {
    for (const user of session.Users) {
      if (user.id === search.Id) {
        session.removeUserById(search.Id);
        await updateSessionInDB(session);
      }
    }
  }

  await deleteUserFromDB(search);
});



// Handle Session creation in store
export const onSessionCreate = functions.firestore.document('sessions/{id}').onCreate(async (session) => {
  const ses = await findSessionById(session.id);
  if (ses === null) return;

  ses.Movies = [];
  const movieIds = await getMoviesFromSearchParams(ses.SearchParams);

  for (const movieId of movieIds) {
    ses.addMovie({ id: movieId, score: 0 });
  }

  functions.logger.log("New movies", ses.Movies);


  const owner = await findUserById(ses.OwnerId);

  if (owner) {
    owner.addSession(ses.Id);
    await updateUserInDB(owner);

    ses.addUser({ id: owner.Id, username: owner.Username, done: owner.isDone() });
  }


  ses.Id = session.id;
  ses.State = SessionState.READY;
  
  await updateSessionInDB(ses);
});

// Handle Session update in store
export const onSessionUpdate = functions.firestore.document('sessions/{id}').onUpdate(async (session) => {
  
  const before = sessionFromSnapshot(session.before.data());
  const after = sessionFromSnapshot(session.after.data());

  
  // Handle change in search parameters
  if (!after.SearchParams.isEqualTo(before.SearchParams)) {

    functions.logger.log("Current movies", after.Movies);

    const movieIds = await getMoviesFromSearchParams(after.SearchParams);
    after.Movies = [];

    for (const movieId of movieIds) {
      after.addMovie({ id: movieId, score: 0 });
    }

    functions.logger.log("New movies", after.Movies);

    after.State = SessionState.READY;
    await updateSessionInDB(after);
  }

  // Handle "Session is done"
  after.checkIfDone();
  // TODO
});

// * Handle User addition to Session
export const addUserToSession = functions.https.onCall(async (data: { userId: string, sessionId: string }) => {
  const user = await findUserById(data.userId);
  const session = await findSessionById(data.sessionId);

  if (session === null || user === null) return;

  if (!session.hasUser(user.Id)) session.addUser({ id: user.Id, username: user.Username, done: user.isDone() });
  await updateSessionInDB(session);

  if (!user.hasSession(session.Id)) user.addSession(session.Id);
  await updateUserInDB(user);
});

// * Handle User deletion from Session
export const removeUserFromSession = functions.https.onCall(async (data: { userId: string, sessionId: string }) => {
  const user = await findUserById(data.userId);
  const session = await findSessionById(data.sessionId);

  if (session === null || user === null) return;

  if (session.hasUser(user.Id)) session.removeUserById(data.userId);
  await updateSessionInDB(session);

  if (!user.hasSession(session.Id)) user.removeSession(session);
  await updateUserInDB(user);
});

// * Update movie score in DB
export const updateMovieScore = functions.https.onCall(async (data: { session: Session }, context) => {
  const session = await findSessionById(data.session.Id);
  if (session === null) return;

  for (const sMovie of session.Movies) {
    for (const dMovie of data.session.Movies) {
      if (sMovie.id === dMovie.id) sMovie.score += dMovie.score;
    }
  }

  const uid = context.auth?.uid;
  if (uid === undefined) return;
  
  session.setUserDone(uid);
});


// Retrieve movie data on call
export const retrieveMoviesData = functions.https.onCall(async (data: { sessionId: string, lang: string }) => {
  const session = await findSessionById(data.sessionId);
  if (session === null) return;


  const moviesData: {
    id: string,
    title: string,
    poster_path: string,
    description: string,
    year: string,
    score: number
  }[] = [];


  for (const movie of session.Movies) {
    moviesData.push(await getLocalizedMovieData({ movieId: movie.id, lang: data.lang }));
  }


  return { movies: moviesData };
});
