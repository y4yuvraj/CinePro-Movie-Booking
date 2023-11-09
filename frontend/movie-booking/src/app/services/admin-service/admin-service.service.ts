import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_BASE_URL } from '../link';

@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {
  private baseUrl= API_BASE_URL;

  constructor(private http:HttpClient) { }

  public addMovie(movie:any)
  {
    return this.http.post(`${this.baseUrl}/addMovie`, movie);
  }

  public viewMovies()
  {
    return this.http.get(`${this.baseUrl}/all`);
  }

  public deleteMovie(movieId:any, movieName:any)
  {
    return this.http.delete(`${this.baseUrl}/${movieName}/delete/${movieId}`);
  }

  public viewAllBookings()
  {
    return this.http.get(`${this.baseUrl}/viewBookedTickets`);
  }

  public viewStatus(movieName:any)
  {
    return this.http.get(`${this.baseUrl}/viewBookedTickets/${movieName}`);
  }

}
