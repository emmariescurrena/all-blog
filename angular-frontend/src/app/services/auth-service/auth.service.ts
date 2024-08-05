import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../../consts/url.constants';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { JwtService } from '../jwt-service/jwt.service';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(
        private httpClient: HttpClient,
        private jwtService: JwtService
    ) { }

    createUser(registerUserDto: RegisterUserDto): Observable<any> {
        return this.httpClient
            .post<RegisterUserDto>(`${BACKEND_URL}/auth/signup`, registerUserDto, { observe: 'response' })
    }

    loginUser(LoginUserDto: LoginUserDto): Observable<any> {
        return this.httpClient
            .post<LoginUserDto>(`${BACKEND_URL}/auth/login`, LoginUserDto, { observe: 'response' })
    }

    isLoggedIn() {
        return !this.jwtService.tokenExpired()
    }

    login(token: string) {
        this.jwtService.saveToken(token);
    }

    logout() {
        this.jwtService.removeToken();
    }

}
