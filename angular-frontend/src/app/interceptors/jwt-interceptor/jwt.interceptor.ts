import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth-service/auth.service';
import { BACKEND_URL } from '../../consts/url.constants';

export function JwtInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {

    const isApiUrl = req.url.startsWith(BACKEND_URL);
    const isLoggedIn = inject(AuthService).isLoggedIn();
    if (!isApiUrl || !isLoggedIn) {
        return next(req);
    }
    const token = inject(AuthService).userValue?.token;
    req = req.clone({
        headers: req.headers.set("Authorization", `Bearer ${token}`)
    });
    return next(req);

}
