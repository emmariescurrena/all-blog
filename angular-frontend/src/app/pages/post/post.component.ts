import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PostService } from '../../services/post-service/post.service';
import { Post } from '../../models/post/post';
import { User } from '../../models/user/user';
import { AuthService } from '../../services/auth-service/auth.service';
import { MarkdownModule } from 'ngx-markdown';

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [RouterModule, MarkdownModule],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit {
    public loading = false;
    public user: User | null;
    public post!: Post;

    constructor(
        private postService: PostService,
        private route: ActivatedRoute
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
                    this.loading = false;
                },
                error: e => console.log(e)
            });
        });
    }

}
