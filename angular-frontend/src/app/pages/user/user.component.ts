import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user.service';
import { User } from '../../models/user/user';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-user',
    standalone: true,
    imports: [],
    templateUrl: './user.component.html',
    styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit {

    public loading = false;
    public user: User | null;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute
    ) {
        this.user = null;
    }

    ngOnInit() {
        this.loading = true;
        this.route.params.subscribe(params => {
            const id = params["id"];
            this.loadUser(id);
        });
    }

    loadUser(id: number) {
        this.userService.getUserById(id).subscribe({
            next: user => {
                this.user = user;
                this.loading = false;
            },
            error: e => console.log(e)
        });
    }
}
