# all-blog, Blog for all
all-blog is an app that allows users to create, edit and delete posts and view others' posts and profiles. It uses md parsing to give posts a better look.

## Purpose
At first, this project was going to be a personal blog. But, being that I wanted to build something more sophisticated using Spring and Angular, I ended up deciding to do this project. It had to be simple, as it was originally going to be, but allowing more people to create posts.

## Quickstart

Export variables from .env:
```
export $(cat .env | xargs)
```

Run backend (maven):
```
./mvnw spring-boot:run
```

Run frontend:
```
ng serve --open --watch
```
