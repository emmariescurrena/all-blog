import { Routes } from '@angular/router';
import { RegisterComponent } from './core/auth/pages/register/register.component';
import { LoginComponent } from './core/auth/pages/login/login.component';

export const routes: Routes = [
    { path: "register", component: RegisterComponent },
    { path: "login", component: LoginComponent },
];
