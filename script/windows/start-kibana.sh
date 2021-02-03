@echo off
docker run -d --name zhihu-kibana -p 5601:5601 -e ELASTICSEARCH_HOSTS=http://zhihu-elasticsearch:9200 --network zhihu kibana:7.10.1
