import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
    public error = "";

    public loginForm = new FormGroup({
        email: new FormControl("", Validators.required),
        password: new FormControl("", Validators.required),
    });


    constructor(
        private authService: AuthService,
        private router: Router
    ) { }

    onSubmitForm() {
        const loginUserDto = new LoginUserDto();

        loginUserDto.email = this.loginForm.value.email!;
        loginUserDto.password = this.loginForm.value.password!;

        this.loginUser(loginUserDto);
    }

    loginUser(loginUserDto: LoginUserDto) {
        this.authService.loginUser(loginUserDto).subscribe({
            next: res => {
                this.router.navigate(["/home"]);
                this.authService.login(res.body.token);
            },
            error: e => {
                console.log(e);
                this.error = e.error.description;
            }
        });;
    }

}
