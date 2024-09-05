import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user.service';
import { User } from '../../models/user/user';

@Component({
    selector: 'app-user-list',
    standalone: true,
    imports: [],
    templateUrl: './user-list.component.html',
    styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

    public loading = false;
    public users!: User[];

    constructor(
        private userService: UserService,
    ) { }

    ngOnInit() {
        this.loading = true;
        this.userService.getAllUsers().subscribe({
            next: users => {
                this.users = users;
                this.loading = false;
            },
            error: e => console.log(e)
        });
    }
}
