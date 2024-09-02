import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BACKEND_URL } from '../../consts/url.constants';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';
import { UpdateUserDto } from '../../dtos/update-user-dto/update-user-dto';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private httpClient: HttpClient,
    ) { }

    createUser(registerUserDto: RegisterUserDto): Observable<any> {
        return this.httpClient
            .post(`${BACKEND_URL}/auth/signup`, registerUserDto, { observe: 'response' })
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

    updateUser(updateUserDto: UpdateUserDto, userId: number): Observable<any> {
        return this.httpClient
            .patch(`${BACKEND_URL}/users/${userId}`, updateUserDto, { observe: 'response' })
    }

    deleteUser(userId: number): Observable<any> {
        return this.httpClient
            .delete(`${BACKEND_URL}/users/${userId}`, { observe: 'response' })
    }
}
