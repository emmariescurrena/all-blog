import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from '../../services/jwt-service/jwt.service';
import { AuthService } from '../../services/auth-service/auth.service';

export function JwtInterceptor(req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> {

    if (inject(AuthService).isLoggedIn()) {
        const token = inject(JwtService).getToken();
        req = req.clone({
            headers: req.headers.set("Authorization", `Bearer ${token}`)
        });
        return next(req);
    }

    return next(req);
}
