import { Routes } from '@angular/router';
import { HomeComponent } from './components/home-component/home-component';
import { LoginComponent } from './components/login-component/login-component';
import { DashboardComponent } from './components/dashboard-component/dashboard-component';
import { CanActivateGuard } from './guard/canActivate/can-activate-guard';

export const routes: Routes = [
    {
        path: '',
        title: 'Techflix Home Page',
        component: HomeComponent,
    },
    {
        path: 'profile',
        title: 'Techflix Dashboard',
        component: DashboardComponent,

        canActivate: [CanActivateGuard],
    children: [
        { path: 'index', component: DashboardComponent },
    //   { path: 'profile', component: ProfileComponent },
    //   { path: 'security', component: SecurityComponent },
      { path: '', redirectTo: 'index', pathMatch: 'full' }
    ]

    },
    
    {
    path: 'login',
    title: 'Techflix Login',
    component: LoginComponent,
    },

    { 
        path: '**', 
        redirectTo: '/' 
    }
];
