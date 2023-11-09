import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public login:LoginService,private router:Router) { }



  ngOnInit(): void {
  }

  logout(){
    this.login.logout();
    this.router.navigate(['/']);
  }

  dashboard()
  {
    if(this.login.getUserRole()=='ROLE_ADMIN')
          {
              window.location.href='/admin-dashboard';
          }else if(this.login.getUserRole()=='ROLE_USER')
          {
              window.location.href='/user-dashboard';
          }
          else
          {
            this.login.logout();
          }
  }

}
