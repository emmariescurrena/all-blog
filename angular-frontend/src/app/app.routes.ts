import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './guards/auth.guard';
import { UserComponent } from './pages/user/user.component';

export const routes: Routes = [
    { path: "register", component: RegisterComponent },
    { path: "login", component: LoginComponent },
    { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
    { path: "", redirectTo: "/home", pathMatch: "full" },
    { path: "users/:id", component: UserComponent, canActivate: [AuthGuard] },
];
