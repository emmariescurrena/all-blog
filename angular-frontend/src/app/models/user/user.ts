import { Role } from "../role/role";

export class User {
    id!: number;
    email!: string;
    name!: string;
    surname!: string;
    role!: Role;
}
