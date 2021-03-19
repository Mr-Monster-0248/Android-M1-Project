import * as functions from 'firebase-functions';
import fetch from 'node-fetch';
import SearchParams, { Query } from '../models/search-params';



const BASE_URL = 'https://api.themoviedb.org/3';
const API_KEY = `api_key=${functions.config().tmdb.key}`;



// Build query string from provided search parameters
function queryStringFromParams(query: Query): string {
  let res = API_KEY;

  const entries = Object.entries(query);
  
  for (const [key, value] of entries) {
    res += `${key}=${value}&`;
  }

  return res + `page=${Math.floor(Math.random() * 10) + 1}`;
}



// Get a list of movies from provided search parameters
export async function getMoviesFromSearchParams(searchParams: SearchParams) {
  const res = await fetch(`${BASE_URL}/discover/movie?${queryStringFromParams(searchParams.Query)}`);
  const { result } = await res.json();
  
  return result
    .map((movie: any) => { movie.id })
    .slice(0, searchParams.Max_nbr);
}