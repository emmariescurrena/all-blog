import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post-service/post.service';
import { Post } from '../../models/post/post';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [RouterModule],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

    public loading = false;
    public posts!: Post[];

    constructor(
        private postService: PostService
    ) { }

    ngOnInit() {
        this.loading = true;
        this.postService.getAllPosts().subscribe({
            next: posts => {
                this.posts = posts;
                this.loading = false;
            },
            error: e => console.log(e)
        });
    }
}
