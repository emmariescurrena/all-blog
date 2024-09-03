import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../services/post-service/post.service';
import { Post } from '../../models/post/post';

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [],
    templateUrl: './post.component.html',
    styleUrl: './post.component.scss'
})
export class PostComponent {
    public loading = false;
    public post: Post | null;

    constructor(
        private postService: PostService,
        private route: ActivatedRoute
    ) {
        this.post = null;
    }

    ngOnInit() {
        this.loading = true;
        this.route.params.subscribe(params => {
            const id = params["id"];
            this.loadPost(id);
        });
    }

    loadPost(id: number) {
        this.postService.getPost(id).subscribe({
            next: post => {
                this.post = post;
                this.loading = false;
            },
            error: e => console.log(e)
        });
    }
}
