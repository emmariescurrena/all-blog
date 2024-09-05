import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostService } from '../../services/post-service/post.service';
import { Router } from '@angular/router';
import { PostDto } from '../../dtos/post-dto/post-dto';

@Component({
    selector: 'app-create-post',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './create-post.component.html',
    styleUrl: './create-post.component.scss'
})
export class CreatePostComponent {
    public errors = [];

    public createForm = new FormGroup({
        title: new FormControl("", Validators.required),
        body: new FormControl("", Validators.required)
    })

    constructor(
        private postService: PostService,
        private router: Router
    ) { }

    onSubmitForm() {
        const postDto = new PostDto();

        postDto.title = this.createForm.value.title!;
        postDto.body = this.createForm.value.body!;

        this.commitPost(postDto).subscribe({
            next: res => {
                // From http://<backendUrl>/posts/<postId>, gets postId 
                const postId = res.headers.get('Location').split("/")[4];
                this.router.navigate([`/posts/${postId}`]);
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });
    }

    commitPost(postDto: PostDto) {
        return this.postService.createPost(postDto);
    }
}
