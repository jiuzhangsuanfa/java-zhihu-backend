package com.jiuzhang.zhihu.util.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

public class CodeGenerator {

    public static final String URL = "jdbc:mysql://localhost:3306/zhihu?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    public static final String projectPath = System.getProperty("user.dir");

    public static void main(String[] args) {

        GlobalConfig gc = getGlobalConfig();
        DataSourceConfig dsConfig = getDataSourceConfig();
        PackageConfig pc = getPackageConfig();

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

        // 如果模板引擎是
        String templatePathDTO = "_templates/entityDTO.java.vm";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePathDTO) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/generated" + "/src/main/java/"
                        +"com/jiuzhang/zhihu/entity/"+ "dto/"+ pc.getModuleName()
                        + "/" + tableInfo.getEntityName() +"DTO"+ StringPool.DOT_JAVA;
            }
        });

        String templatePathVO = "_templates/entityVO.java.vm";
        focList.add(new FileOutConfig(templatePathVO) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/generated" + "/src/main/java/"
                        +"com/jiuzhang/zhihu/entity/"+"vo/"+ pc.getModuleName()
                        + "/" + tableInfo.getEntityName() +"VO"+ StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);


        TemplateConfig templateConfig = getTemplateConfig();
        StrategyConfig strategy = getStrategyConfig(pc);

        // 执行代码生成器
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

    private static StrategyConfig getStrategyConfig(PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("BaseController");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
//        strategy.setInclude("question", "answer", "vote_stats");
        strategy.setInclude("vote_stats");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        return strategy;
    }

    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig()
                    .setEntity("_templates/entity.java")
                    .setController("_templates/controller.java")
                    .setService("_templates/service.java")
                    .setServiceImpl("_templates/serviceImpl.java")
                    .setMapper("_templates/mapper.java")
                    .setXml("_templates/mapper.xml");
    }

    /** 全局配置 */
    public static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
//        gc.setOutputDir(projectPath + "/src/main/java/")
        gc.setOutputDir(projectPath + "/generated" +"/src/main/java/")
                .setAuthor("作者")
                .setFileOverride(true)
                .setOpen(false)
                .setIdType(IdType.AUTO)
                .setBaseResultMap(true)
                .setBaseColumnList(true);
        return gc;
    }

    /** 数据源配置 */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setUrl(URL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root");
        return dsConfig;
    }

    /** 包路径配置 */
    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("zhihu");
        pc.setParent("com.jiuzhang.zhihu")
                .setController("controller")
                .setService("service")
                .setEntity("entity")
                .setMapper("mapper")
                .setXml("mapper");
        return pc;
    }

}
