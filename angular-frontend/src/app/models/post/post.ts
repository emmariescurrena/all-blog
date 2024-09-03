import { User } from "../user/user";

export interface Post {
    id: number;
    title: string;
    body: string;
    user: User;
}
