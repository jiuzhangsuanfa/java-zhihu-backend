@echo off
docker run -d --name zhihu-minio -p 9000:9000 -e "MINIO_ACCESS_KEY=minioadmin" -e "MINIO_SECRET_KEY=minioadmin" -v zhihu-minio:/data minio/minio:RELEASE.2020-12-26T01-35-54Z --network zhihu server /data
