import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, RouterOutlet } from '@angular/router';
import { User } from './models/user/user';
import { AuthService } from './services/auth-service/auth.service';
import { DOCUMENT } from '@angular/common';


@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, RouterModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
    public title = 'all-blog';
    public user!: User | null;
    public infoMessage!: string;
    private theme: 'light' | 'dark' = 'dark';

    constructor(
        private authService: AuthService,
        private route: ActivatedRoute,
        @Inject(DOCUMENT) private document: Document
    ) {
        this.applyTheme();
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

    public changeTheme() {
        this.theme = this.theme == 'dark' ? 'light' : 'dark';
        this.applyTheme();
    }

    private applyTheme() {
        this.document.body.setAttribute('data-bs-theme', this.theme);
    }

}
