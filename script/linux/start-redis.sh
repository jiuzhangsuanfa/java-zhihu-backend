#!/usr/bin/env bash
docker run -d \
    --name zhihu-redis \
    -p 6379:6379 \
    -v zhihu-redis:/data \
    --network zhihu \
    redis:6.0.9
