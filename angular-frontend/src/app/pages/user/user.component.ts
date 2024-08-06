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

    public user = new User();

    constructor(
        private userService: UserService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.route.params.subscribe(params => {
            const id = params['id'];
            this.loadUser(id);
        });
    }

    loadUser(id: number) {
        this.userService.getUser(id).subscribe({
            next: res => this.user = res,
            error: e => console.log(e)
        });
    }
}
