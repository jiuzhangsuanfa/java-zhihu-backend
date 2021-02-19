docker run -d `
    --name zhihu-elasticsearch `
    -p 9200:9200 `
    -p 9300:9300 `
    -e "discovery.type=single-node" `
    -v zhihu-elasticsearch:/usr/share/elasticsearch/data `
    --network zhihu `
    elasticsearch:7.10.1
