import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { AuthService } from '../../services/auth-service/auth.service';
import { isPlatformServer } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ReactiveFormsModule, RouterModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
    public error!: string;
    public isServer = false;

    public loginForm = new FormGroup({
        email: new FormControl("", Validators.required),
        password: new FormControl("", Validators.required),
    });


    constructor(
        private authService: AuthService,
        @Inject(PLATFORM_ID) platformId: Object,
        private route: ActivatedRoute
    ) {
        this.isServer = isPlatformServer(platformId);
    }


    onSubmitForm() {
        const loginUserDto = new LoginUserDto();

        loginUserDto.email = this.loginForm.value.email!;
        loginUserDto.password = this.loginForm.value.password!;

        this.loginUser(loginUserDto).subscribe({
            error: e => this.error = e.error.description
        });
    }

    loginUser(loginUserDto: LoginUserDto) {
        return this.authService.login(loginUserDto);
    }

}
