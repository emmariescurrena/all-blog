import { Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './guards/auth.guard';
import { UserComponent } from './pages/user/user.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { AnonymousGuard } from './guards/anonymous.guard';
import { Role } from './models/role/role';
import { LogoutComponent } from './logout/logout.component';
import { ProfileComponent } from './profile/profile.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { PostComponent } from './pages/post/post.component';

export const routes: Routes = [
    { path: "register", component: RegisterComponent, canActivate: [AnonymousGuard] },
    { path: "login", component: LoginComponent, canActivate: [AnonymousGuard] },
    { path: "home", component: HomeComponent, canActivate: [AuthGuard] },
    { path: "", redirectTo: "/home", pathMatch: "full" },
    { path: "logout", component: LogoutComponent, canActivate: [AuthGuard] },
    { path: "users/me", component: ProfileComponent, canActivate: [AuthGuard] },
    { path: "users/:id", component: UserComponent, canActivate: [AuthGuard] },
    { path: "posts/create-post", component: CreatePostComponent, canActivate: [AuthGuard] },
    { path: "posts/:id", component: PostComponent, canActivate: [AuthGuard] },
    {
        path: "users",
        component: UserListComponent,
        canActivate: [AuthGuard],
        data: { roles: [Role.Admin, Role.SuperAdmin] }
    },
];
