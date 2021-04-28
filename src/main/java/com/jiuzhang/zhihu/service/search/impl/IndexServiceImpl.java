package com.jiuzhang.zhihu.service.search.impl;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.service.IQuestionService;
import com.jiuzhang.zhihu.service.search.IndexService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class IndexServiceImpl implements IndexService {

    private Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    IQuestionService questionService;


    @Override
    public SearchHits search(String keyword) {

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("content", keyword);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        sourceBuilder.query(boolBuilder);

        SearchRequest request = new SearchRequest("zhihu_question");
        request.source(sourceBuilder);

        SearchResponse response;
        try {
            logger.info("search request:" +request);

            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            return response.getHits();
        } catch (IOException e) {
            // log.error();
            return null;
        }
    }

    @Override
    public int index(long questionId) {
        Question question = questionService.getById(questionId);
        return doIndex(question);
    }

    @Override
    public int indexAll() {
        List<Question> questions = questionService.list();
        for (Question question : questions) {
            doIndex(question);
        }
        return 0;
    }

    @Override
    public int delete(long questionId) {
        DeleteRequest request = new DeleteRequest("zhihu_question");
        request.id(String.valueOf(questionId));
        try {
            DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            return response.getResult().hashCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    // -------------------------------------------- 内部方法 --------------------------------------------
    /** 写入索引 */
    private int doIndex(Question question) {
        if (question==null) {
            return 0;
        }

        IndexRequest request = new IndexRequest("zhihu_question", "_doc",
                String.valueOf(question.getId()) );
        request.id(String.valueOf(question.getId()));
        request.source(JSON.toJSONString(question), XContentType.JSON);

        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
