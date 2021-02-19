#!/usr/bin/env bash
docker network create zhihu
script/linux/start-elasticsearch.sh \
& script/linux/start-keycloak.sh \
& script/linux/start-kibana.sh \
& script/linux/start-minio.sh \
& script/linux/start-mysql.sh \
& script/linux/start-redis.sh
