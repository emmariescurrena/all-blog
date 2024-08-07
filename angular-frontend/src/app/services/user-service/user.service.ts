import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BACKEND_URL } from '../../consts/url.constants';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    getUserById(id: number): Observable<User> {
        return this.httpClient.get<User>(`${BACKEND_URL}/users/byId/${id}`);
    }

    getUserByEmail(email: string): Observable<User> {
        return this.httpClient.get<User>(`${BACKEND_URL}/users/byEmail/${email}`);
    }

    getAllUsers(): Observable<User[]> {
        return this.httpClient.get<User[]>(`${BACKEND_URL}/users`);
    }
}
