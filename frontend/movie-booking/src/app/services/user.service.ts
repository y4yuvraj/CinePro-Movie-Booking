import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_BASE_URL } from './link';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = API_BASE_URL;

  constructor(private http: HttpClient) { }

  public registerUser(user: any) {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  public bookMovie(bookingDetails:any)
  {
    return this.http.post(`${this.baseUrl}/bookTickets`, bookingDetails);
  }

}
