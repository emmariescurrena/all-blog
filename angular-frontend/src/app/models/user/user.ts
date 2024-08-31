import { Role } from "../role/role";

export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
    role: Role;
    token?: string;
}
