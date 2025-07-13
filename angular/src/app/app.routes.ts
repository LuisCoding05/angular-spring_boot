import { Routes } from '@angular/router';
import { HomeComponent } from './components/home-component/home-component';

export const routes: Routes = [
    {
        path: '',
        title: 'Techflix Home Page',
        component: HomeComponent,
    },
    // {
    // path: 'dashboard',
    // title: 'Techflix Dashboard',
    // // component: Home,
    // },
    // {
    // path: 'login',
    // title: 'Techflix Login',
    // // component: Home,
    // }

    { 
        path: '**', 
        redirectTo: '/' 
    }
];
