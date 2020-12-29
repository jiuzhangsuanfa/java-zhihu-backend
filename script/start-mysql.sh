#!/bin/bash
docker run -d \
  --name mysql-zhihu \
  -p 3306:3306 \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=true \
  -e MYSQL_DATABASE=zhihu \
  -e MYSQL_USER=zhihu \
  -e MYSQL_PASSWORD=zhihu \
  mysql:8.0.22
