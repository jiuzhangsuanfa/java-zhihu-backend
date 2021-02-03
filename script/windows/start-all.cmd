@echo off
docker network create zhihu
./start-elasticsearch.cmd
./start-keycloak.cmd
./start-minio.cmd
./start-mysql.cmd
./start-redis.cmd
