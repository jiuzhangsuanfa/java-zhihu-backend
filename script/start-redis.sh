#!/bin/bash
docker run -d \
  --name redis-zhihu \
  -p 6379:6379 \
  redis:6.0.9
