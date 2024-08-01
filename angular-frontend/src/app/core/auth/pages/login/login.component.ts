import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginUserDto } from '../../dtos/login-user-dto/login-user-dto';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
    public errors = [];

    public loginForm = new FormGroup({
        email: new FormControl("", Validators.required),
        password: new FormControl("", Validators.required),
    });


    constructor(
        private router: Router
    ) { }

    onSubmitForm() {
        const loginUserDto = new LoginUserDto();

        loginUserDto.email = this.loginForm.value.email!;
        loginUserDto.password = this.loginForm.value.password!;

    }

    redirectToIndex() {
        this.router.navigate([""]);
    }
}
