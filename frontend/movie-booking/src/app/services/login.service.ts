import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_BASE_URL, BASE_BASE_URL } from './link';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = API_BASE_URL;
  private base_baseUrl=BASE_BASE_URL;

  constructor(private http: HttpClient) { }

  public getCurrentUser(){
    return this.http.get(`${this.base_baseUrl}/currentuser`);
  }

  public generateToken(loginData: any) {
    // console.log(loginData);
    this.logout();
    return this.http.post(`${this.base_baseUrl}/authenticate`, loginData);
  }

  public loginUser(token: any) {
    localStorage.setItem('secret', token)
    return true
  }

  public isLoggedIn() {
    let tokenStr = localStorage.getItem('secret');
    if (tokenStr == undefined || tokenStr == '' || tokenStr == null) {
      return false;
    } else {
      return true;
    }
  }

  public logout(){
    localStorage.removeItem('secret');
    return true;
  }

  public getSecret(){
    return localStorage.getItem('secret');
  }

  public setUser(user:any){
    localStorage.setItem('user',JSON.stringify(user));
  }

  public getUser()
  {
    let userStr=localStorage.getItem('user');
    if(userStr!=null)
    {
      return JSON.parse(userStr);
    }else{
      this.logout();
      return null;
    }
  }

  public getUserRole()
  {
    let user=this.getUser();
    return user.authorities[0].authority;
  }

  public resetpassword(loginId:any)
  {
    return this.http.get(`${this.baseUrl}/${loginId}/forgot`);
  }

}
 