package com.rehe.database.admin;//package com.rehe.modules.admin;
//
//
//import org.mybatis.generator.api.MyBatisGenerator;
//import org.mybatis.generator.config.Configuration;
//import org.mybatis.generator.exception.InvalidConfigurationException;
//import org.mybatis.generator.internal.DefaultShellCallback;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author xiech
// * @description
// * @date 2024/1/5
// */
//public class MyBatisGeneratorMain {
//    // 数据库连接
//    private final static String url = "jdbc:mysql://10.211.55.7:3306/rehe_admin_master?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    // 生成作者
//    private final static String author = "xiech";
//    // 输出路径 当前模块src/java
//    private final static String outputDir = "/rehe-modules/rehe-admin/src/main/java/";
//
//    // 设置父包名 主包
//    private final static String parent = "com.rehe.modules.admin";
//    // 设置父包模块名 模块名
//    private final static String moduleName = "system";
//    // xml路径
//    private final static String xmlFileDir = "/rehe-modules/rehe-admin/src/main/resources/admin_mapper/";
//
//    private final static String tableName = "system_user";
//
//    public static void main(String[] args)   {
//    try {
//        List<String> warnings = new ArrayList<>();
//        boolean overwrite = true;
//        Configuration config = GeneratorConfig.getGeneratorConfig();
//
//
//        //   ... fill out the config object as appropriate...
//
//        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//        myBatisGenerator.generate(null);
//
//        String projectPath = System.getProperty("user.dir");
//    }catch (Exception e){
//        System.out.println(e.getMessage());
//        e.printStackTrace();
//    }
//
////
////        FastAutoGenerator.create(url,"root", "Xiehong123")
////                .globalConfig(builder -> {
////                    builder.author(author) // 设置作者
//////                            .enableSwagger() // 开启 swagger 模式
////                            .outputDir(projectPath+outputDir); // 指定输出目录
////                })
////                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
////                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
////                    if (typeCode == Types.SMALLINT) {
////                        // 自定义类型转换
////                        return DbColumnType.INTEGER;
////                    }
////                    return typeRegistry.getColumnType(metaInfo);
////
////                }))
////                .packageConfig(builder -> {
////                    builder.parent(parent) // 设置父包名
////                            .moduleName(moduleName) // 设置父包模块名
////                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath+xmlFileDir+moduleName)); // 设置mapperXml生成路径
////                })
////                .strategyConfig(builder -> {
////                    builder.addInclude(tableName)
////                      .addTablePrefix("system_") // 设置需要生成的表名
////                     .controllerBuilder()
////                     .enableRestStyle()
////                     .enableHyphenStyle()
////                     .entityBuilder()
////                     .enableLombok().build();
////                })
////                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
////                .execute();
////    }
//    }
//}
