import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BACKEND_URL } from '../../consts/url.constants';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    createUser(registerUserDto: RegisterUserDto): Observable<any> {
        return this.httpClient
            .post<RegisterUserDto>(`${BACKEND_URL}/auth/signup`, registerUserDto, { observe: 'response' })
    }

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
