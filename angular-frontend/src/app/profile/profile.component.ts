import { Component } from '@angular/core';
import { User } from '../models/user/user';
import { AuthService } from '../services/auth-service/auth.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpdateUserDto } from '../dtos/update-user-dto/update-user-dto';
import { UserService } from '../services/user-service/user.service';

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent {
    public user: User | null;
    public errors = [];

    public updateForm = new FormGroup({
        email: new FormControl("", Validators.required),
        password: new FormControl("", Validators.required),
        confirmPassword: new FormControl("", Validators.required),
    });

    constructor(
        private userService: UserService,
        private authService: AuthService
    ) {
        this.user = authService.userValue;
    }

    onSubmitForm() {
        const updateUserDto = new UpdateUserDto();

        updateUserDto.email = this.updateForm.value.email!;
        updateUserDto.password = this.updateForm.value.password!;
        updateUserDto.confirmPassword = this.updateForm.value.confirmPassword!;

        this.commitUser(updateUserDto);
    }

    commitUser(updateUserDto: UpdateUserDto) {
        this.userService.updateUser(updateUserDto, this.user?.id!).subscribe({
            next: res => {
                this.authService.logout();
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });
    }

    onDelete() {
        this.userService.deleteUser(this.user?.id!).subscribe({
            next: res => {
                this.authService.logout();
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });
    }
}
