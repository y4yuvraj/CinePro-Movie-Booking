import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AdminServiceService } from 'src/app/services/admin-service/admin-service.service';

@Component({
  selector: 'app-admin-view-bookings',
  templateUrl: './admin-view-bookings.component.html',
  styleUrls: ['./admin-view-bookings.component.css']
})
export class AdminViewBookingsComponent implements OnInit {
  bookings!: any[]; 

  constructor(private adminService:AdminServiceService, private snack:MatSnackBar) { }

  ngOnInit(): void {
    this.adminService.viewAllBookings().subscribe(
      (response:any)=>{
        this.bookings = response;
      },
      (error)=>
      {
        this.snack.open('Something Went Wrong! Try again','',{
          duration:2000,
        });
      }
    )
  }

}
