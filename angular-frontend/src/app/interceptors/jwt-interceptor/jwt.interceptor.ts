import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from '../../services/jwt-service/jwt.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

    constructor(
        private jwtService: JwtService,
    ) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.includes("/login") || req.url.includes("/signup")) {
            return next.handle(req);
        }

        const token = this.jwtService.getToken();
        if (token) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.jwtService.getToken()}`
                }
            });
            return next.handle(req);
        }

        return next.handle(req);
    }
}
