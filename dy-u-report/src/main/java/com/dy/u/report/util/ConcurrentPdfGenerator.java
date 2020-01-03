package com.dy.u.report.util;

import com.dy.s.basic.util.FileUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @Description 多线程化的pdf生成器，避免过度同步
 * @author dxy
 * @date 2019-12-04
 *
 */
public class ConcurrentPdfGenerator {
	
  private ConcurrentPdfGenerator(){}

  private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentPdfGenerator.class);

  /**
   * 每个线程各自获取TextRenderer，避免线程之间共用
   * 
   * @return
   * @throws Exception
   */
  public static ITextRenderer newTextRenderer() throws Exception {
    ITextRenderer renderer = new ITextRenderer();
    final List<String> fontPathList = ReportUtil.getFontPathList();
    // 添加字体
    ITextFontResolver fontResolver = renderer.getFontResolver();
    try {
      //fontResolver.addFont("/Users/runningcoder/Desktop/pdf/font/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

      for (String fontPath : fontPathList){

          if(fontPath.endsWith(".ttf")||fontPath.endsWith(".ttc")){

            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
          }

      }
    } catch (Exception e) {
      LOGGER.error("pdf字体解析错误", e);

      throw e;
    }

    return renderer;
  }

  /**
   * 由ftl模板生成对应的pdf文件
   * 
   * @param renderer
   *          pdf渲染器，一个线程只能有一个，需要通过 {@link
   *          com.cfets.fee.s.basic.export.pdf.newTextRenderer() }得到
   * 
   * @param templateName
   *          模板名字,不需要加上后缀名
   * @param cssName
   *          css样式的名字，不需要加后缀名
   * @param dataMap
   *          数据字典
   * @param outputPdfPath
   *          pdf输出路径
   * @param fontPathList
   *          字体路径路径，推荐使用宋体等中文字体，以解决中文乱码或中文不输出问题
   * @throws Exception
   */
  public static void generate(ITextRenderer renderer, String templateName, String cssName,
      Map<String, Object> dataMap, String outputPdfPath, List<String> fontPathList)
      throws Exception {
    if (fontPathList == null) {
      throw new Exception( "通知单字体未配置");
    }

    // 删除掉后缀名
    int index = templateName.lastIndexOf(".");
    if (index != -1) {
      templateName = templateName.substring(0, index);
    }

    String cssPath = HtmlGenerator.concurrentGenerateCss(cssName, dataMap);
    dataMap.put("reportCssName", FileUtil.getFileNameByPath(cssPath));
    String htmlPath = HtmlGenerator.generateHTML(templateName, dataMap);

    try {
      generate(renderer, htmlPath, outputPdfPath, fontPathList);
    } catch (Exception e) {
      LOGGER.error(String.format("PDF生成出错，对应参数： 模板名 - %s; html中间文件路径 - %s;ftl字典 - %s",
          templateName, htmlPath, dataMap.toString()), e);

      throw e;
    }

    // 删除中间的html文件和css文件
    FileUtil.delFile(htmlPath);
    FileUtil.delFile(cssPath);
  }

  /**
   * 由ftl模板生成对应的pdf文件
   * 
   * @param templateName
   *          模板名字,不需要加上后缀名
   * @param dataMap
   *          数据字典
   * @param outputPdfPath
   *          pdf输出路径
   * @param fontPathList
   *          字体路径路径，推荐使用宋体等中文字体，以解决中文乱码或中文不输出问题
   * @throws Exception
   */
  public static void generate(ITextRenderer renderer, String templateName,
      Map<String, Object> dataMap, String outputPdfPath, List<String> fontPathList)
      throws Exception {
    if (fontPathList == null) {
      throw new Exception("通知单字体未配置");
    }

    // 删除掉后缀名
    int index = templateName.lastIndexOf(".");
    if (index != -1) {
      templateName = templateName.substring(0, index);
    }


    String htmlPath = HtmlGenerator.generateHTML(templateName, dataMap);

    try {
      generate(renderer, htmlPath, outputPdfPath, fontPathList);
    } catch (Exception e) {
      LOGGER.error(String.format("PDF生成出错，对应参数： 模板名 - %s; html中间文件路径 - %s;ftl字典 - %d",
          templateName, htmlPath, dataMap), e);

      throw e;
    }

    // 删除中间的html文件
    File htmlFile = new File(htmlPath);
    htmlFile.delete();
  }

  /**
   * 根据html文件生成通知单
   * 
   * @param htmlPath
   *          html的文件位置
   * @param outputPdfPath
   *          pdf输出路径
   * @param fontPathList
   *          字体路径列表，推荐使用宋体等中文字体，以解决中文乱码
   * @throws IOException
   * @throws DocumentException
   */
  public static void generate(ITextRenderer renderer, String htmlPath, String outputPdfPath,
      List<String> fontPathList) throws Exception {
    if (fontPathList == null) {
      throw new Exception( "通知单字体未配置");
    }

    OutputStream out = new FileOutputStream(outputPdfPath);

    String htmlURL = new File(htmlPath).toURI().toURL().toString();

    // ITextOutputDevice users the xhtml`s title if no title has been set
    // before the pdf meta-data.
    renderer.getOutputDevice().setMetadata("title", null);
    renderer.setDocument(htmlURL);
    renderer.layout();
    renderer.createPDF(out);

    out.close();
  }

  /**
   * 根据html字符流生成pdf
   * 
   * @param htmlStr
   *          待转换的HTML字符串刘
   * @param outputPdfPath
   *          输出pdf路径名： D:\cafiles\strategy\report\ENVELOPE\FX\20150817\
   *          20150817_000000000000000000017.pdf 或者
   *          /opt/feefiles/strategy/report/FX/20150817/
   *          20150817_000000000000000000017.pdf
   * @param fontPathList
   * @throws FactoryConfigurationError
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   * @throws UnsupportedEncodingException
   * @throws DocumentException
   * @throws
   */
  public static void generateWithHTMLString(ITextRenderer renderer, String htmlStr,
      String outputPdfPath, List<String> fontPathList) throws Exception {
    if (fontPathList == null) {
      throw new Exception("通知单字体未配置");
    }

    OutputStream out = new FileOutputStream(outputPdfPath);
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

    // 字符编码问题
    Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));

    // ITextOutputDevice users the xhtml`s title if no title has been set
    // before the pdf meta-data.
    renderer.getOutputDevice().setMetadata("title", null);
    renderer.setDocument(doc, null);
    renderer.layout();
    renderer.createPDF(out);

    out.close();
  }
}
