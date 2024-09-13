import { Component, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router, RouterModule, RouterOutlet } from '@angular/router';
import { User } from './models/user/user';
import { AuthService } from './services/auth-service/auth.service';


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, RouterModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
    title = 'all-blog';
    public user!: User | null;
    public infoMessage!: string;

    constructor(
        private authService: AuthService,
        private route: ActivatedRoute,
    ) {
        this.authService.user.subscribe(user => this.user = user);
    }

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
            if (params['infoMessage']) {
                this.infoMessage = params['infoMessage'];
            } else {
                this.infoMessage = "";
            }
        });
    }

}
