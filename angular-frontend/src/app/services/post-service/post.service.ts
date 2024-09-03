import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostDto } from '../../dtos/post-dto/post-dto';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../../consts/url.constants';
import { Post } from '../../models/post/post';


@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(
        private httpClient: HttpClient
    ) { }

    createPost(postDto: PostDto): Observable<any> {
        return this.httpClient
            .post(`${BACKEND_URL}/posts`, postDto, { observe: 'response' })
    }

    getPost(id: number): Observable<Post> {
        return this.httpClient.get<Post>(`${BACKEND_URL}/posts/${id}`);
    }

    getAllUsers(): Observable<Post[]> {
        return this.httpClient.get<Post[]>(`${BACKEND_URL}/posts`);
    }

    updatePost(postDto: PostDto, postId: number): Observable<any> {
        return this.httpClient
            .patch(`${BACKEND_URL}/posts/${postId}`, postDto, { observe: 'response' })
    }

    deletePost(postId: number): Observable<any> {
        return this.httpClient
            .delete(`${BACKEND_URL}/posts/${postId}`, { observe: 'response' })
    }

}
