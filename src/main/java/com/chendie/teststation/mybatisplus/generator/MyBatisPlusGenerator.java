package com.chendie.teststation.mybatisplus.generator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
public class MyBatisPlusGenerator {
  private static final String URL = "jdbc:mysql://localhost:3306/test_station?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
  private static final String USERNAME = "root";
//  private static final String PASSWORD = "cyhcd5201314";
//  private static final String OUTPUT_DIR = "/Users/chendie/Documents/JavaWorkplace/idea/test-station/src/main/java";

  private static final String PASSWORD = "1314520ASD@";
  private static final String OUTPUT_DIR = "G:\\My_code_project_tmp\\test-station\\src\\main\\java";


  public static void main(String[] args) {
    FastAutoGenerator.create(URL, USERNAME, PASSWORD)
      .globalConfig(builder -> {
        builder.author("chendie") // 设置作者
//          .enableSwagger() // 开启 swagger 模式
          .fileOverride() // 覆盖已生成文件
          .outputDir(OUTPUT_DIR); // 指定输出目录
      })
      .packageConfig(builder -> {
        builder.parent("com.chendie.teststation"); // 设置父包名
//                            .moduleName("system") // 设置父包模块名
//                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
//                                    "D://")); // 设置mapperXml生成路径
      })
      .strategyConfig(builder -> {
        builder.addInclude("user","paper","user_paper","question",
                "paper_question","lesson","exam_record","exam_record_detail",
                "information","user_information","tag"); // 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
        builder
          .entityBuilder()
          .enableLombok();
      })
      // 使用Freemarker引擎模板，默认的是Velocity引擎模板
      .templateEngine(new FreemarkerTemplateEngine())
      .execute();

  }
}
