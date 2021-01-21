@echo off
docker run -d --name zhihu-mysql -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_DATABASE=zhihu -e MYSQL_USER=zhihu -e MYSQL_PASSWORD=zhihu -v zhihu-mysql:/var/lib/mysql mysql:8.0.22
