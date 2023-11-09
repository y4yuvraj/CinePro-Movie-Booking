import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';



@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  // private confirmpassword: any;
  public passwordMatch = true;
  public userForm!: FormGroup;

  constructor(private _snackBar: MatSnackBar, private userService: UserService, private router: Router) { }


  ngOnInit(): void {
    this.userForm = new FormGroup({
      loginId: new FormControl('', Validators.required), 
      password: new FormControl('', Validators.required),
      confirmpassword: new FormControl(''),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      contactNumber: new FormControl('', [Validators.required, Validators.pattern(/^\d{10}$/)]),
    });
  }

  formSubmit() {
    if (this.passwordMatch && this.userForm.valid) {
      this.userService.registerUser(this.userForm.value)
        .subscribe(
          (response) => {
            Swal.fire('Success', 'user is registered', 'success').then(
              (result) => {
                if (result.isConfirmed) {
                  this.router.navigate(['/']);
                }
              }
            )
            this.userForm.reset(); 
          },
          (error) => {
            if (error instanceof HttpErrorResponse) {
              const errorMessage = error.error.errorMessage;
              this._snackBar.open(errorMessage, 'Close', {
                duration: 2000,
              });
            } else {
              // console.log(error);
              this._snackBar.open('An error occurred.', 'Close', {
                duration: 2000,
              });
            }
          }
        );
    } else {
      this._snackBar.open('Please fill out the form correctly!!', 'Close', {
        duration: 2000,
      });
    }
  }

  checkPasswordMatch() {
    const password = this.userForm.get('password')?.value;
    const confirmPassword = this.userForm.get('confirmpassword')?.value;

    if (password !== confirmPassword) {
      this._snackBar.open('Passwords do not match!!', 'Close', {
        duration: 2000,
      });
      this.passwordMatch = false;
    } else {
      this.passwordMatch = true;
    }
  }

  clearForm(){
    this.userForm.reset(); 
  }

}
