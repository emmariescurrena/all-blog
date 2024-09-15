import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt-interceptor/jwt.interceptor';
import { CoolStorageModule } from '@angular-cool/storage';
import { provideMarkdown } from 'ngx-markdown';

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(routes),
        provideClientHydration(),
        provideHttpClient(withFetch(), withInterceptors([JwtInterceptor])),
        importProvidersFrom(CoolStorageModule.forRoot()),
        provideMarkdown(),
    ],
};

