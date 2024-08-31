import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth-service/auth.service';

@Injectable({
    providedIn: 'root',
})
export class AnonymousGuard implements CanActivate {
    constructor(
        private authService: AuthService,
        private router: Router) { }

    canActivate(): boolean {
        if (!this.authService.isLoggedIn()) {
            return true;
        } else {
            this.router.navigate(["/home"]);
            return false;
        }
    }
}