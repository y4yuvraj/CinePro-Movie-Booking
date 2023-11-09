import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject, retry } from 'rxjs';
import { AdminServiceService } from 'src/app/services/admin-service/admin-service.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-view-movie',
  templateUrl: './view-movie.component.html',
  styleUrls: ['./view-movie.component.css']
})
export class ViewMovieComponent implements OnInit {
  movies: any[] = [];
  public isAdmin = false;
  public isUser = true;
  public quantity!: number;
  public bookingDetails = {
    movieName: "",
    theatreName: "",
    numberOfTickets: 0
  }
  dtOptions:DataTables.Settings={}
  dtTrigger:Subject<any>=new Subject<any>();

  constructor(private adminService: AdminServiceService,
    private snack: MatSnackBar, private login: LoginService, private user: UserService) { }

  ngOnInit(): void {
    this.dtOptions={
      pagingType:'simple'
    }
    if (this.login.getUserRole() == "ROLE_ADMIN") {
      this.isAdmin = true;
      this.isUser = false;
    }
    else {
      this.isAdmin = false;
      this.isUser = true;
    }
    this.adminService.viewMovies().subscribe(
      (data: any) => {
        this.movies = data;
        this.dtTrigger.next(null);
      }
    ),
      (error: any) => {
        this.snack.open('An error occurred.', 'Close', {
          duration: 2000,
        });
      }
  }

  public getStatus(movieName: string) {
    this.adminService.viewStatus(movieName).subscribe(
      (response: any) => {
        const formattedData = `
        Available Tickets: ${response.availableTickets}<br>
        Booking Status: ${response.bookingStatus}<br>
        Number of Users Who Booked Tickets: ${response.numberOfUsersBookedTicket}<br>
        Total Tickets Booked: ${response.totalTicketBooked}
        `;
        const div = document.createElement('div');
        div.innerHTML = formattedData;
        Swal.fire({
          icon: 'info',
          title: 'Movie Status',
          html: div
        });
      },
      (error: any) => {
        this.snack.open(error.error.errorMessage, '')
      }
    )
  }


  deleteMovie(movieName: string, movieId: string) {
    Swal.fire({
      title: 'Confirm Delete',
      text: `Are you sure you want to delete the movie '${movieName}'?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.adminService.deleteMovie(movieId, movieName).subscribe(
          (response: any) => {
            this.snack.open(response.message, '', {
              duration: 2000
            });
            this.movies = this.movies.filter(movie => movie.movieId !== movieId);
          },
          (error) => {
            console.log(error);
            this.snack.open(error.error.errorMessage, '', {
              duration: 2000
            });
          }
        );
      }
    });
  }

  public bookMovie(movieName: string, theatreName: string, numberOfTickets: number) {

    this.bookingDetails.movieName = movieName;
    this.bookingDetails.theatreName = theatreName;
    this.bookingDetails.numberOfTickets = numberOfTickets;
    this.user.bookMovie(this.bookingDetails).subscribe(
      (response: any) => {
        this.snack.open(response.message, '', {
          duration: 2000
        });
        const movie = this.movies.find((m) => m.movieName === movieName);
        if (movie) {
          movie.availableTickets -= numberOfTickets;
        }
      },
      (error) => {
        console.log(error);
        this.snack.open("something went wrong", '', {
          duration: 2000
        });
      }
    )
  }

  openTicketQuantityDialog(movieName: string, theatreName: string) {
    Swal.fire({
      title: 'Enter Number of Tickets',
      input: 'number',
      inputAttributes: {
        min: '1',
      },
      inputValidator: (value) => {
        if (!value) {
          return 'You need to enter the number of tickets';
        }
        return;
      },
      showCancelButton: true,
      confirmButtonText: 'Book',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        const numberOfTickets = parseInt(result.value, 10);
        this.bookMovie(movieName, theatreName, numberOfTickets);
      }
    });
  }

}
