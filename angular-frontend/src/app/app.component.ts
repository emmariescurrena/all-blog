import { Component, Output } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { User } from './models/user/user';
import { AuthService } from './services/auth-service/auth.service';


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, RouterModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'all-blog';
    @Output() public user: User | null | undefined;

    constructor(
        private authService: AuthService,
    ) {
        this.authService.user.subscribe(user => this.user = user);
    }

}
