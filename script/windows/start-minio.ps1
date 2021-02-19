docker run -d `
    --name zhihu-minio `
    -p 9000:9000 `
    -e "MINIO_ACCESS_KEY=minioadmin" `
    -e "MINIO_SECRET_KEY=minioadmin" `
    -v zhihu-minio:/data `
    --network zhihu `
    minio/minio:RELEASE.2020-12-26T01-35-54Z server /data
