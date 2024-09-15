import { Component, inject } from '@angular/core';
import { Post } from '../../models/post/post';
import { PostService } from '../../services/post-service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostDto } from '../../dtos/post-dto/post-dto';
import { User } from '../../models/user/user';
import { AuthService } from '../../services/auth-service/auth.service';
import { MarkdownModule } from 'ngx-markdown';

@Component({
    selector: 'app-edit-post',
    standalone: true,
    imports: [ReactiveFormsModule, MarkdownModule],
    templateUrl: './edit-post.component.html',
    styleUrl: './edit-post.component.scss'
})
export class EditPostComponent {
    public errors!: [];
    public loading = false;
    public user: User | null;
    public originalPost!: Post;
    public updateForm!: FormGroup;
    public editedTitle!: string;
    public editedBody!: string;

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
                next: originalPost => {
                    this.originalPost = originalPost;
                    this.updateForm = new FormGroup({
                        title: new FormControl("", Validators.required),
                        body: new FormControl("", Validators.required)
                    });
                    this.editedTitle = this.originalPost?.title;
                    this.editedBody = this.originalPost?.body;
                    if (this.user?.id !== this.originalPost.user.id) {
                        this.router.navigate(
                            [`/posts/${this.originalPost.id}`],
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
                this.router.navigate([`/posts/${this.originalPost.id}`]);
            },
            error: e => {
                this.errors = e.error.errors;
            }
        });
    }

    commitPost(postDto: PostDto) {
        return this.postService.updatePost(postDto, this.originalPost.id);
    }

    onDelete() {
        this.postService.deletePost(this.originalPost.id).subscribe({
            next: res => this.router.navigate(['/home']),
            error: e => this.errors = e.error.errors
        });
    }
}
