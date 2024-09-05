import { Component } from '@angular/core';
import { Post } from '../../models/post/post';
import { PostService } from '../../services/post-service/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PostDto } from '../../dtos/post-dto/post-dto';

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
    public post!: Post;
    public updateForm!: FormGroup;

    constructor(
        private postService: PostService,
        private route: ActivatedRoute,
        private router: Router
    ) { }

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
                    this.loading = false;
                },
                error: e => console.log(e)
            });
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
}
