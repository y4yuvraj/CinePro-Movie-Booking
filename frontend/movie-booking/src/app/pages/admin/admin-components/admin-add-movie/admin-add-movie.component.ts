import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminServiceService } from 'src/app/services/admin-service/admin-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-add-movie',
  templateUrl: './admin-add-movie.component.html',
  styleUrls: ['./admin-add-movie.component.css']
})
export class AdminAddMovieComponent implements OnInit {
  public movieForm!: FormGroup;

  constructor(private adminService:AdminServiceService, private _snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.movieForm = new FormGroup({
      movieName: new FormControl('', Validators.required), 
      theatreName: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
      totalTickets: new FormControl('', Validators.required),
      availableTickets: new FormControl('', Validators.required),
    });
  }

  formSubmit()
  {
    if(this.movieForm.valid)
    {
      this.adminService.addMovie(this.movieForm.value).subscribe(
        (response)=>{
          Swal.fire('Success', 'Movie Added', 'success')
          this.clearForm();

          setTimeout(() => {
            window.location.href = "http://localhost:4200/admin-dashboard/admin-view-movies";
          }, 2000);
        },
        (error)=>
        {
          if (error instanceof HttpErrorResponse) {
            const errorMessage = error.error.errorMessage;
            this._snackBar.open(errorMessage, 'Close', {
              duration: 2000,
            });
          } else {
            this._snackBar.open('An error occurred.', 'Close', {
              duration: 2000,
            });
          }

        }
      )
    }else
    {
      this._snackBar.open('Please fill out the form correctly!!', '', {
        duration: 2000,
      }); 
    }
    
  }

  clearForm(){
    this.movieForm.reset(); 
  }

}
