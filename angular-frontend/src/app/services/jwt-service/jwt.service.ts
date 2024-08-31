import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class JwtService {
    constructor() {
    }

    tokenExpired(token: string) {
        const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
        return (Math.floor((new Date).getTime() / 1000)) >= expiry;
    }

    getUserInfo(token: string) {
        let payload = token.split(".")[1];
        payload = window.atob(payload);
        return JSON.parse(payload);
    }

}
