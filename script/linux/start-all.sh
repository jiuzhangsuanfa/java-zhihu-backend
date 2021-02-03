#!/usr/bin/env bash
docker network create zhihu
script/start-elasticsearch.sh \
& script/start-minio.sh \
& script/start-mysql.sh \
& script/start-redis.sh \
& script/start-keycloak.sh
