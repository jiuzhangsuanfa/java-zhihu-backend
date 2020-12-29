#!/bin/bash
docker run -d \
  --name minio-zhihu \
  -p 9000:9000 \
  -e "MINIO_ACCESS_KEY=minioadmin" \
  -e "MINIO_SECRET_KEY=minioadmin" \
  minio/minio:RELEASE.2020-12-26T01-35-54Z server /data
