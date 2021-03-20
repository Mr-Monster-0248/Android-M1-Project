import * as functions from 'firebase-functions';
import fetch from 'node-fetch';
import SearchParams, { Query } from '../models/search-params';



const BASE_URL = 'https://api.themoviedb.org/3';
const API_KEY = functions.config().tmdb.key;



// Build query string from provided search parameters
function queryStringFromParams(query: Query): string {
  let res = `api_key=${API_KEY}`;

  const entries = Object.entries(query);
  
  for (const [key, value] of entries) {
    res += `&${key}=${value}`;
  }

  return res + `&page=${Math.floor(Math.random() * 10) + 1}`;
}



// Get a list of movies from provided search parameters
export async function getMoviesFromSearchParams(searchParams: SearchParams) {
  const res = await fetch(`${BASE_URL}/discover/movie?${queryStringFromParams(searchParams.Query)}`);
  const json = await res.json();
  
  return json
    .results
    .map((movie: any) => movie.id)
    .slice(0, searchParams.Max_nbr);
}



// Retrieve localized movie data
export async function getLocalizedMovieData(query: { movieId: string, lang: string }) {
  const res = await fetch(`${BASE_URL}/movie/${query.movieId}?api_key=${API_KEY}&language=${query.lang}`);
  const json = await res.json();

  const result = {
    id: json.id,                                // string
    title: json.title,                          // string
    poster_path: `http://image.tmdb.org/t/p/w300/${json.poster_path}`,              // string
    description: json.overview,                 // string
    year: json.release_date.substring(0, 4),    // string
    score: 0                                    // number
  };

  return result;
}