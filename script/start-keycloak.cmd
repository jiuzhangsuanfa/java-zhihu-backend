@echo off
docker run -d \
  --name zhihu-keycloak \
  -p 8080:8080 \
  -e KEYCLOAK_USER=jiuzhang \
  -e KEYCLOAK_PASSWORD=jiuzhang \
  quay.io/keycloak/keycloak:12.0.1
