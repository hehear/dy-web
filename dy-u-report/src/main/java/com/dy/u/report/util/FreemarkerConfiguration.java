package com.dy.u.report.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @Descriprion freemaker 配置类
 * @author dxy
 * @date 2019-12-04
 *
 */
public final class FreemarkerConfiguration {
  private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerConfiguration.class);

  private static Configuration config;
  private static String templateDirectoryPath;
  private static Random random = new Random(10086);

  /**
   * Static initialization.
   * 
   * Initialize the configuration of Freemarker.
   */
  static {
    config = new Configuration();


    templateDirectoryPath = "/Users/runningcoder/git/dy-web/dy-web/target/template/";


    File file = new File(templateDirectoryPath);
    try {
      config.setDirectoryForTemplateLoading(file);
    } catch (IOException e) {

      LOGGER.error(e.getMessage());
    }
    config.setObjectWrapper(new DefaultObjectWrapper());
    config.setDefaultEncoding("UTF-8");
  }

  /**
   * 返回模板对应的html路径，要求唯一，所以使用了 {@link java.util.UUID#randomUUID()} 和
   * {@link Random#Random(long)}
   * 
   * @param templateName
   * @return
   */
  public static String getTemplateHtmlPath(String templateName) {
    // 产生精确的唯一的标识
    String htmlPath = String.format("%s/temp/%s-%d-%d.html", templateDirectoryPath, templateName,
        System.nanoTime(), random.nextLong());

    return htmlPath;
  }

  /**
   * 获取模板所在的目录
   * 
   * @return
   */
  public static String getTemplateDir() {

    // 产生唯一的标识
    return templateDirectoryPath + "/temp/";
  }

  public static Configuration getConfiguration() {
    return config;
  }

  public void setConfiguration(String baseDirectory) throws IOException {
    config.setDirectoryForTemplateLoading(new File(baseDirectory));
  }
}
