Swagger http://localhost:8081/swagger-ui/index.html

Docer
docker run -d --name library-db -p 5432:5432 -e POSTGRES_DB=library -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin postgres:13
docker start library-db

docker build -t myapp .

docker run -d --name myapp-container -p 8081:8081 myapp
