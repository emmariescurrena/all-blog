import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user-service/user.service';
import { User } from '../../models/user/user';
import { JwtService } from '../../services/jwt-service/jwt.service';

@Component({
    selector: 'app-user-list',
    standalone: true,
    imports: [],
    templateUrl: './user-list.component.html',
    styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {
    public users!: User[];

    constructor(
        private userService: UserService
    ) { }

    ngOnInit() {
        this.loadUsers();
    }

    loadUsers() {
        this.userService.getAllUsers().subscribe({
            next: users => this.users = users,
            error: e => console.log(e)
        });

    }
}
