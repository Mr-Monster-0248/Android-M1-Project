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
}


export interface Query {
  genreIds: string[];
  include_adult: boolean;
}