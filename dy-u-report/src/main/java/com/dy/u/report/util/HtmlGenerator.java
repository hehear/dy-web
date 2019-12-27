package com.dy.u.report.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Random;

import com.dy.s.basic.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description html生成工具类，由freemarker模板生成html文件
 * @author dxy
 * @date 2019-12-04
 */
public class HtmlGenerator {
	
  private HtmlGenerator(){}

  private static Random random = new Random(10010);

  /***
   * 根据路径名获取ftl模板
   * 
   * @param templateName
   *          模板名字（name）
   * @return
   * @throws IOException
   */
  private static Template getTemplate(String templateName) throws IOException {
    // ftl文件所在文件
    Configuration config = FreemarkerConfiguration.getConfiguration();

    Template template = config.getTemplate(templateName + ".ftl");

    return template;
  }

  /***
   * 由ftl模板生成对应的html文件，返回html文件的路径
   * 
   * @param templateName
   *          模板名字（name）
   * @param dataMap
   *          数据模型
   * @return
   * @throws Exception
   */
  public static String generateHTML(String templateName, Map<String, Object> dataMap)
      throws Exception {

    String htmlPath = FreemarkerConfiguration.getTemplateHtmlPath(templateName);

    File outFile = new File(htmlPath);
    FileOutputStream fos = new FileOutputStream(outFile);

    // 字符编码设置好
    Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

    Template tp = getTemplate(templateName);
    tp.setEncoding("UTF-8");
    tp.process(dataMap, out);
    out.flush();

    fos.close();
    out.close();

    return htmlPath;
  }

  /**
   * 生成通知单css文件
   * 
   * @param cssName
   *          css模板名称
   * @param dataMap
   *          数据字典
   * @throws Exception
   */
  public static void generateCss(String cssName, Map<String, Object> dataMap) throws Exception {
    String cssPath = FreemarkerConfiguration.getTemplateDir() + "ntc.css";

    // 通知单的css样式已经存在，重新生成
    FileUtil.delFile(cssPath);

    File outFile = new File(cssPath);
    FileOutputStream fos = new FileOutputStream(outFile);

    // 字符编码设置好
    Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

    Template tp = getTemplate(cssName);
    tp.setEncoding("UTF-8");
    tp.process(dataMap, out);
    out.flush();

    fos.close();
    out.close();
  }

  /**
   * 生成通知单css文件
   * 
   * @param cssName
   *          css模板名称
   * @param dataMap
   *          数据字典
   * 
   * @return cssPath css路径，文件名：ntc-当前毫秒.css
   * 
   * @throws Exception
   */
  public static String concurrentGenerateCss(String cssName, Map<String, Object> dataMap)
      throws Exception {
    // 要求唯一，所以使用了 {@link java.util.UUID#randomUUID()} 和 {@link java.util.Random#Random(long)}
    String cssPath = String.format("%sntc-%d-%d.css", FreemarkerConfiguration.getTemplateDir(),
        System.nanoTime(), random.nextLong());

    File outFile = new File(cssPath);
    FileOutputStream fos = new FileOutputStream(outFile);

    // 字符编码设置好
    Writer out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

    Template tp = getTemplate(cssName);
    tp.setEncoding("UTF-8");
    tp.process(dataMap, out);
    out.flush();

    fos.close();
    out.close();

    return cssPath;
  }
}
