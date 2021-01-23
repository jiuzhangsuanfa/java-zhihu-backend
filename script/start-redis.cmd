@echo off
docker run -d --name zhihu-redis -p 6379:6379 -v zhihu-redis:/data redis:6.0.9
