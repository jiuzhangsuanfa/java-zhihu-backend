package com.jiuzhang.zhihu;

import com.jiuzhang.zhihu.entity.Question;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchTest {

    public static final String INDEX = "zhihu_question";

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 新建索引
     * @throws Exception
     */
    @Test
    void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        CreateIndexResponse createIndexResponse = restHighLevelClient
                .indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 索引存在
     * @throws Exception
     */
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    /**
     * 创建文档
     * @throws IOException
     */
    @Test
    void testCreateDocument() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX);
//        User user = new User("张飞","射手");
        Question question = new Question();
//        IndexRequest source = indexRequest.source(JSON.toJSONString(question), XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());
    }

    /**
     * 文档是否存在
     * @throws IOException
     */
    @Test
    void testExistDocument() throws IOException {
        //testapi 索引中     是否存在 1 的文档
        GetRequest getRequest = new GetRequest(INDEX, "1");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 获取文档信息
     * @throws IOException
     */
    @Test
    void testGetDocument() throws IOException {
        GetRequest getRequest = new GetRequest(INDEX, "gBd0W3MBYL0QvcF5Z9tv");
        GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSource());
    }

    /**
     * 获取文档信息
     * @throws IOException
     */
    @Test
    void testUpdatDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, "jxeBW3MBYL0QvcF5idvD");
//        User user = new User("张飞","坦克");
//        updateRequest.doc(JSONObject.toJSONString(user),XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    /**
     * 删除文档信息
     * @throws IOException
     */
    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, "jxeBW3MBYL0QvcF5idvD");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    /**
     * 查询文档
     */
    @Test
    void testSearchDocument() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        //匹配字段
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("username", "李白");
        //构建查询器
        searchRequest.source(new SearchSourceBuilder().query(matchQueryBuilder));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getHits().getTotalHits());
    }
}
