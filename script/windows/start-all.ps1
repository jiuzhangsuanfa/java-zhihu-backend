docker network create zhihu
script\linux\start-elasticsearch.ps1 `
& script\linux\start-keycloak.ps1 `
& script\linux\start-kibana.ps1 `
& script\linux\start-minio.ps1 `
& script\linux\start-mysql.ps1 `
& script\linux\start-redis.ps1
