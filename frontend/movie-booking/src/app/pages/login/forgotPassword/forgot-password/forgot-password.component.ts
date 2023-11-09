import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { DialogData } from '../DiaglogData';
import { LoginService } from 'src/app/services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  resetLoginId: any;
  newPassword: any;

  constructor(
    public dialogRef: MatDialogRef<ForgotPasswordComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData, private login: LoginService, private sncak: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  public resetPassword() {
    if (this.resetLoginId != null || this.resetLoginId != '') {
      this.login.resetpassword(this.resetLoginId).subscribe(
        (data) => {
          this.newPassword = data;
          this.newPassword = this.newPassword.password
        },
        (error) => {
          console.log(error)
          this.sncak.open(error.error.errorMessage, '', {
            duration: 2000,
          });
        }
      )

    }

  }

}
