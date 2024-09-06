import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { BACKEND_URL } from '../../consts/url.constants';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { JwtService } from '../jwt-service/jwt.service';
import { CoolLocalStorage } from '@angular-cool/storage';
import { Router } from '@angular/router';
import { User } from '../../models/user/user';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private localStorage: CoolLocalStorage;
    private userSubject: BehaviorSubject<User | null>;
    public user: Observable<User | null>;

    constructor(
        private httpClient: HttpClient,
        private jwtService: JwtService,
        private router: Router,
        localStorage: CoolLocalStorage
    ) {
        this.userSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')!));
        this.user = this.userSubject.asObservable();
        this.localStorage = localStorage;
    }

    public get userValue() {
        return this.userSubject.value;
    }

    createUser(registerUserDto: RegisterUserDto): Observable<any> {
        return this.httpClient.post(`${BACKEND_URL}/auth/signup`, registerUserDto)
    }

    login(loginUserDto: LoginUserDto) {
        return this.httpClient
            .post<User>(`${BACKEND_URL}/auth/login`, loginUserDto)
            .pipe(map(user => {
                localStorage.setItem('user', JSON.stringify(user));
                this.userSubject.next(user);
                this.router.navigate(['/home']);
                return user;
            }))
    }

    isLoggedIn() {
        const token = this.userValue?.token;
        if (!token) {
            return false;
        }
        return !this.jwtService.tokenExpired(token);
    }

    logout() {
        this.localStorage.removeItem('user');
        this.userSubject.next(null);
        this.router.navigate(['/login']);
    }


}
