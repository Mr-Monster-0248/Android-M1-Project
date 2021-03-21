# Chill

The Netflix and Chill application without the hasle of chosing your movie

---

## Usage

### Authentication

The application uses **Firebase** as a backend, for now we only use fake account that you can play with:

| email             | password   | informations                          |
| ----------------- | ---------- | ------------------------------------- |
| test@mail.com     | Qwerty1234 | Own a lot of session allready created |
| thibault@mail.com | Qwerty1234 | Has joined some session               |

You also can creat your own user ! you just need to register with an email and a password

### Create or Join a session

Before accessing the voting activity you will need to create or join a session. The session is sharable with everyone with the session ID

#### Creating a session

To create a session you only need to give it a name and fill the information asked by the UI (the genres, the adult content, and the number of movies by session)

#### Joining a session

Joining a session has been thinked to be very easy, you just need the session ID that your friend can give you
