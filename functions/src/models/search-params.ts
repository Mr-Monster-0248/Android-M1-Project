import { checkArraysEqual } from "../utils/misc.util";

export default class SearchParams {
  constructor(
    private query: Query,
    private max_nbr: number
  ) {
    this.query = query;
    this.max_nbr = max_nbr;
  }


  get Query(): Query {
    return this.query;
  }

  get Max_nbr(): number {
    return this.max_nbr;
  }


  isEqualTo(other: SearchParams) {
    return this.Max_nbr === other.Max_nbr
      && this.Query.include_adult === other.Query.include_adult
      && checkArraysEqual<number>(this.Query.with_genres, other.Query.with_genres);
  }
}


export interface Query {
  with_genres: number[];
  include_adult: boolean;
}