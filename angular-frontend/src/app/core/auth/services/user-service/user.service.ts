import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';
import { BACKEND_URL } from '../../consts/url.constants';
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private httpClient: HttpClient
    ) { }

    createUser(registerUserDto: RegisterUserDto): Observable<any> {
        return this.httpClient
            .post<RegisterUserDto>(`${BACKEND_URL}/auth/signup`, registerUserDto, { observe: 'response' })
    }

    getUser(id: number) {
        this.httpClient
            .get<RegisterUserDto>(`${BACKEND_URL}/users/${id}`, { observe: 'response' })
            .subscribe(res => {
                console.log('Response status:', res.status);
                console.log('Body:', res.body);
            })
    }

}
