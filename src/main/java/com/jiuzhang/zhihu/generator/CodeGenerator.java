package com.jiuzhang.zhihu.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

public class CodeGenerator {

    public static final String URL = "jdbc:mysql://localhost:3306/zhihu?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
//    public static final String URL = "jdbc:mysql://localhost:3306/zhihu";

    public static void main(String[] args) {

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = "D:\\codegen\\"; // System.getProperty("user.dir");
//        String projectPath = "D:\\九章算法\\第一讲 系统设计：问答系统\\zhihu\\"; // System.getProperty("user.dir");
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "\\src\\main\\java\\")
                .setAuthor("九章算法")
                .setFileOverride(true)
                .setOpen(false)
                .setIdType(IdType.AUTO)
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        // 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setUrl(URL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root");


        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("zhihu");
        pc.setParent("com.jiuzhang.zhihu")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("mapper");


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        // 如果模板引擎是
        String templatePathDTO = "_templates/entityDTO.java.vm";
        focList.add(new FileOutConfig(templatePathDTO) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/jiuzhang/zhihu/entity/dto/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        });

        String templatePathVO = "_templates/entityVO.java.vm";
        focList.add(new FileOutConfig(templatePathVO) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/com/jiuzhang/zhihu/entity/vo/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("_templates/entity.java")
                .setController("_templates/controller.java")
                .setService("_templates/service.java")
                .setServiceImpl("_templates/serviceImpl.java")
                .setMapper("_templates/mapper.java")
                .setXml("_templates/mapper.xml");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("BaseController");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("com.jiuzhang.zhihu.common.BaseController");
//        strategy.setSuperControllerP
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude("question", "answer", "vote");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

//        VelocityTemplateEngine templateEngine = new VelocityTemplateEngine();
//        ConfigBuilder configBuilder = new ConfigBuilder(pc,dsConfig,strategy,templateConfig,gc);
//        templateEngine.init(configBuilder);

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(gc);
        generator.setPackageInfo(pc);
        generator.setDataSource(dsConfig);
        generator.setCfg(cfg);
        generator.setTemplate(templateConfig);
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new VelocityTemplateEngine());

        generator.execute();
    }

}
