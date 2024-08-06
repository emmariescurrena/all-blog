import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BACKEND_URL } from '../../consts/url.constants';
import { User } from '../../models/user/user';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    getUser(id: number) {
        return this.httpClient.get<User>(`${BACKEND_URL}/users/${id}`);
    }
}
