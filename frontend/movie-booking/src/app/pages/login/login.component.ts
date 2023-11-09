import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoginService } from 'src/app/services/login.service';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef, MatDialogModule} from '@angular/material/dialog';
import { ForgotPasswordComponent } from './forgotPassword/forgot-password/forgot-password.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public userLogin!: FormGroup;
  public resetLoginId:any;

  constructor(private sncak: MatSnackBar, private login: LoginService,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.userLogin = new FormGroup({
      loginId: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  }

  formSubmit() {
    if (this.userLogin.value.loginId.trim() == '' || this.userLogin.value.loginId == null) {
      this.sncak.open('LoginId is required !', 'ok', {
        duration: 2000,
      })
      return;
    }

    if (this.userLogin.value.password.trim() == '' || this.userLogin.value.password == null) {
      this.sncak.open('Password is required !', 'ok', {
        duration: 2000,
      })
      return;
    }

    this.login.generateToken(this.userLogin.value).subscribe(
      (data: any) => {
        this.login.loginUser(data.token);
        this.login.getCurrentUser().subscribe((user:any)=>{
          this.login.setUser(user);
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
        })
      },
      (error) => {
        console.log('Error !');
        this.sncak.open('Invalid Details! Try again','ok',{
          duration:2000,
        });
      }
    )

  }

  forgotPassword() {
    const dialogRef = this.dialog.open(ForgotPasswordComponent);
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.resetLoginId = result;
      }
    });
  }


}
 