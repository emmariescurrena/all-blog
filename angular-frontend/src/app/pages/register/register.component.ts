import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterUserDto } from '../../dtos/register-user-dto/register-user-dto';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service/user.service';

@Component({
    selector: 'app-register',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss'
})
export class RegisterComponent {
    public errors = [];

    public registerForm = new FormGroup({
        name: new FormControl("", Validators.required),
        surname: new FormControl("", Validators.required),
        email: new FormControl("", Validators.required),
        password: new FormControl("", Validators.required),
        confirmPassword: new FormControl("", Validators.required),
    });


    constructor(
        private userService: UserService,
        private router: Router
    ) { }

    onSubmitForm() {
        const registerUserDto = new RegisterUserDto();

        registerUserDto.name = this.registerForm.value.name!;
        registerUserDto.surname = this.registerForm.value.surname!;
        registerUserDto.email = this.registerForm.value.email!;
        registerUserDto.password = this.registerForm.value.password!;
        registerUserDto.confirmPassword = this.registerForm.value.confirmPassword!;

        this.commitUser(registerUserDto);
    }

    commitUser(registerUserDto: RegisterUserDto) {
        this.userService.createUser(registerUserDto).subscribe({
            next: res => {
                this.router.navigate(["/login"]);
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });;
    }

}
