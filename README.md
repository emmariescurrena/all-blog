# all-blog
Blog for all

Built with Java Spring Boot and Angular

Export variables from .env:
export $(cat .env | xargs)

Run backend (maven):
./mvnw spring-boot:run

Run frontend:
ng serve --open