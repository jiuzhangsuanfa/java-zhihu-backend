//package com.jiuzhang.zhihu.config;
//
//import org.apache.commons.lang3.RandomUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//
///**
// * @author shark
// * @since 2020/8/12
// */
//@Component
//@Order(1)
//public class HeadParseFilter extends OncePerRequestFilter {
//    private static final Logger logger = LoggerFactory.getLogger(HeadParseFilter.class);
//    @Autowired
//    private CommonDataRedis commonDataRedis;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        parse(request);
//        logger.info("requestId：{}，请求接口：{}", RequestInfoHolder.getRequestId(), request.getRequestURL());
//        filterChain.doFilter(request, response);
//    }
//
//    private void parse(HttpServletRequest request) {
//        RequestInfoHolder.newRequest();
//
//        String ip = ToolUtils.getClientIp(request);
//        RequestInfoHolder.setIp(ip);
//
//        long currentTime = DateUtils.getCurrentMillsTime();
//        RequestInfoHolder.setRequestTime(currentTime);
//        String id = String.format("%d%d", currentTime, RandomUtils.nextInt(0, 10000));
//        RequestInfoHolder.setRequestId(id);
//
//        HttpHeader header = Json.toObject(request.getHeader("Head"), HttpHeader.class);
//        if (header != null) {
//            RequestInfoHolder.setHeadExist(true);
//            RequestInfoHolder.setDeviceId(header.getDeviceId());
//            RequestInfoHolder.setModel(header.getModel());
//            RequestInfoHolder.setToken(header.getToken());
//            if (StringUtils.isNotBlank(header.getChannel())) {
//                try {
//                    RequestInfoHolder.setChannel(URLDecoder.decode(header.getChannel(), "utf-8"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//            AccessType type = AccessType.getTypeByAccessId(header.getAccessId());
//            RequestInfoHolder.setAccessType(type);
//            RequestInfoHolder.setVersion(header.getVersionCode());
//
//            if (StringUtils.isNotBlank(header.getToken())) {
//                String key = RedisKeyUtils.tokenKey(SingleSignType.getByAccessType(type).getMark(), header.getToken());
//                if (commonDataRedis.exist(key)) {
//                    String userId = commonDataRedis.get(key, String.class);
//                    Integer uid = NumberUtils.toInt(userId);
//                    RequestInfoHolder.setUid(uid);
//                }
//            }
//        } else {
//            RequestInfoHolder.setHeadExist(false);
//        }
//    }
//}
