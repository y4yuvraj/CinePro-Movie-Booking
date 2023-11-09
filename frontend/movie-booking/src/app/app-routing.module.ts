import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterUserComponent } from './pages/register-user/register-user.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { AdminGuard } from './guards/admin.guard';
import { NormalUserGuard } from './guards/normal-user.guard';
import { AuthGuard } from './guards/auth.guard';
import { AdminAddMovieComponent } from './pages/admin/admin-components/admin-add-movie/admin-add-movie.component';
import { ViewMovieComponent } from './components/view-movie/view-movie.component';
import { AdminViewBookingsComponent } from './pages/admin/admin-components/admin-view-bookings/admin-view-bookings.component';

const routes: Routes = [
  {
    path:'',
    component:HomeComponent
  },
  {
    path:'register-user',
    component:RegisterUserComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]
  },
  {
    path:'login',
    component:LoginComponent,
    pathMatch:'full',
    canActivate:[AuthGuard]
  },
  {
    path:'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate:[AdminGuard],
    children:[
      {
        path:'',
        redirectTo:'add-movie',
        pathMatch:'full'
      },
      {
        path:'add-movie',
        component:AdminAddMovieComponent
      },
      {
        path:'admin-view-movies',
        component:ViewMovieComponent
      },
      {
        path:'view-bookings',
        component:AdminViewBookingsComponent
      }
    ]
  },
  {
    path:'user-dashboard',
    component: UserDashboardComponent,
    canActivate:[NormalUserGuard],
    children:[
      {
        path:'',
        redirectTo:'user-view-movies',
        pathMatch:'full'
      },
      {
        path:'user-view-movies',
        component:ViewMovieComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
