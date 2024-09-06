import { Component, inject } from '@angular/core';
import { Post } from '../../models/post/post';
import { PostService } from '../../services/post-service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostDto } from '../../dtos/post-dto/post-dto';
import { User } from '../../models/user/user';
import { AuthService } from '../../services/auth-service/auth.service';

@Component({
    selector: 'app-edit-post',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './edit-post.component.html',
    styleUrl: './edit-post.component.scss'
})
export class EditPostComponent {
    public errors!: [];
    public loading = false;
    public user: User | null;
    public post!: Post;
    public updateForm!: FormGroup;

    constructor(
        private postService: PostService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.user = inject(AuthService).userValue;
    }

    ngOnInit() {
        this.loading = true;
        this.route.params.subscribe(params => {
            const id = params["id"];
            this.postService.getPost(id).subscribe({
                next: post => {
                    this.post = post;
                    this.updateForm = new FormGroup({
                        title: new FormControl(this.post?.title, Validators.required),
                        body: new FormControl(this.post?.body, Validators.required)
                    });
                    if (this.user?.id !== this.post.user.id) {
                        this.router.navigate(
                            [`/posts/${this.post.id}`],
                            { queryParams: { infoMessage: 'Not allowed to edit this post' } }
                        );
                    }
                },
                error: e => console.log(e)
            });
            this.loading = false;
        });
    }

    onSubmitForm() {
        const postDto = new PostDto();

        postDto.title = this.updateForm.value.title;
        postDto.body = this.updateForm.value.body;

        this.commitPost(postDto).subscribe({
            next: res => {
                this.router.navigate([`/posts/${this.post.id}`]);
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });
    }

    commitPost(postDto: PostDto) {
        return this.postService.updatePost(postDto, this.post.id);
    }

    onDelete() {
        this.postService.deletePost(this.post.id).subscribe({
            next: res => this.router.navigate(['/home']),
            error: e => this.errors = e.error.errors
        });
    }
}
