import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../../consts/url.constants';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { JwtService } from '../jwt-service/jwt.service';
import { UserService } from '../user-service/user.service';
import { User } from '../../models/user/user';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(
        private httpClient: HttpClient,
        private jwtService: JwtService,
        private userService: UserService
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

    isAdmin() {
        const email = this.jwtService.getUserInfo().sub;
        return this.userService.getUserByEmail(email).subscribe({
            next: user => {
                if (user.role.name == "SUPER_ADMIN" || user.role.name == "ADMIN") {
                    return true;
                } else {
                    return false;
                }
            },
            error: e => { return false }
        });
    }

    login(token: string) {
        this.jwtService.saveToken(token);
    }

    logout() {
        this.jwtService.removeToken();
    }

}
