package com.dy.u.report.util;

import com.dy.s.basic.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description 报表处理工具类
 * @author dxy
 * @date 2019-12-04
 */
public class ReportUtil {
	
  private ReportUtil(){}

  private static final String FILE_SEPERATOR = "/";

  private static final String fontDir = "/Users/runningcoder/Desktop/学习/pdf/font";

  /**
   * 替换掉所有的连续空格，html解析的时候会把连续的空格字符合并为一个
   * 
   * @param string
   * @return
   */
  public static String replaceToHtmlBlank(String string) {
    if (string == null) {
      return null;
    }
    string = replaceToHtmlAmp(string); // 替换&，这个是特殊字符
    string = replaceToHtmlLt(string); // 替换<，这个是特殊字符
    string = string.replace("  ", "&#160;&#160;"); // 保留空格,这里不能使用&nbsp

    return string;
  }

  /**
   * 替换字符串中的&为html中的&amp;
   * 
   * @param string
   * @return
   */
  public static String replaceToHtmlAmp(String string) {
    if (string == null) {
      return null;
    }
    return string.replace("&", "&amp;");
  }

  /**
   * 替换字符串中的<为html中的&lt;
   * 
   * @param string
   * @return
   */
  public static String replaceToHtmlLt(String string) {
    if (string == null) {
      return null;
    }
    return string.replace("<", "&lt;");
  }

  /**
   * 获取ftl的中文默认字体
   * 
   * @return SimSun, SimHei, KaiTi, LiSu, YouYuan
   */
  public static String getFontFamilyChinese() {
    return "SimSun, SimHei, KaiTi, LiSu, YouYuan";
  }

  /**
   * 获取ftl的英文默认字体
   * 
   * @return 'Times New Roman', Arial, Tohama, Verdana
   */
  public static String getFontFamilyEnglish() {
    return "'Times New Roman', Arial, Tohama, Verdana";
  }

  /**
   * 获取通知单pdf支持的所有的中英文字体路径
   * 
   * @return 通知单pdf支持的所有的中英文字体路径
   */
  public static List<String> getFontPathList() {

    // 中文字体: 宋体 、黑体、 楷体 、隶书 、幼圆
    // 英文字体: Tohama/Arial/Times New Roman/
    return FileUtil.getAllFileNames(fontDir);
  }

  /**
   * 向客户端以预览的方式发送pdf文件，
   * 
   * @param rep
   * @param pdfPath
   *          pdf文件路径
   * @throws IOException
   */
  public static void previewPdf(HttpServletResponse rep, String pdfPath)
      throws IOException {
    File pdfFile = new File(pdfPath);

    if (!pdfFile.isFile() || !pdfFile.exists() || pdfFile.length() == 0) {
      rep.setContentType("text/html;charset=gb2312");
      PrintWriter out = rep.getWriter();
      out.print("<font color='red'>无此会员的pdf文件</font>");
      out.close();

      return;
    }

    rep.setContentType("application/pdf");
    rep.setStatus(HttpServletResponse.SC_OK);
    // 预览，而不是直接下载
    rep.setHeader("content-disposition", "filename=" + URLEncoder.encode(pdfFile.getName(), "utf-8"));

    FileInputStream inputStream = new FileInputStream(pdfFile);
    OutputStream outputStream = rep.getOutputStream();

    byte[] buffer = new byte[1024];
    int len = 0;

    while ((len = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, len);
    }

    inputStream.close();
    outputStream.close();
  }

  /**
   * 向客户端以下载的方式发送pdf文件，
   * 
   * @param rep
   * @param path
   *          pdf文件路径
   * @param contentType
   *          pdf:application/pdf、excel:application/vnd.ms-excel、csv:text/ csv、
   *          xml:text/xml、doc/dot/rft/wiz：application/msword
   * @throws IOException
   */
  public static void sendFile(HttpServletResponse rep, String path,
      String contentType) throws Exception {
    sendFile(rep, path, contentType, null);
  }

  /**
   * 向客户端以下载的方式发送pdf文件，
   * 
   * @param rep
   * @param path
   *          pdf文件路径
   * @param contentType
   *          pdf:application/pdf、excel:application/vnd.ms-excel、csv:text/ csv、
   *          xml:text/xml、doc/dot/rft/wiz：application/msword
   * @param 浏览器上输出流的文件名
   * @throws IOException
   */
  public static void sendFile(HttpServletResponse rep, String path,
      String contentType, String outputName) throws Exception {
    File file = new File(path);

    if (!file.isFile()) {
      throw new Exception("文件不存在！");
    }
    if (!file.exists() || file.length() == 0) {
      throw new Exception("文件不存在！");
    }

    if (outputName == null) {
      outputName = file.getName();
    }

    rep.setContentType(contentType);
    rep.setStatus(HttpServletResponse.SC_OK);
    // 预览，而不是直接下载，中文名字处理
    rep.setHeader("content-disposition",
        "attachment;filename=" + URLEncoder.encode(outputName, "utf-8"));

    FileInputStream inputStream = new FileInputStream(file);
    OutputStream outputStream = rep.getOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;

    while ((len = inputStream.read(buffer)) > 0) {
      outputStream.write(buffer, 0, len);
    }

    inputStream.close();
    outputStream.close();
  }

  public static String convertNullToHtmlBlank(String string) {
    if (string == null) {
      string = " ";
    }
    return replaceToHtmlBlank(string);
  }

  /**
   * 将word的字体大小转换为html中的字体大小，依据是1em是12
   * 
   * @param fontSizeString
   * @return
   */
  public static String getEM(String fontSizeString) {
    int fontSize = Integer.parseInt(fontSizeString);
    return getEM((double) fontSize);
  }

  /**
   * 将word的字体大小转换为html中的字体大小，依据是1em是12
   * 
   * @param fontSize
   * @return
   */
  public static String getEM(double fontSize) {
    // 1em是12
    double num = fontSize / 12.0;
    return num + "em;";
  }

  /**
   * 将word的字体大小转换为html中的字体大小，依据是1em是12
   * 
   * @param fontSize
   * @return
   */
  public static String getEM(int fontSize) {
    return getEM((double) fontSize);
  }
  
	/**
	 * 将数据map中字符串类型的值，中特殊字符进行转义html中的转义 &转为&amp; <转为&lt; >转为&gt; '转为&apos; "转为&quot;
	 * 
	 * @param Map<String, Object>  dataMap
	 */
	public static void replaceToHtml(final Map<String, Object> dataMap) {
		
		if(dataMap==null){
			return ;
		}
		
		Iterator<String> itor = dataMap.keySet().iterator();
		while (itor.hasNext()) {
			String tempKey = itor.next();
			Object tempObj = dataMap.get(tempKey);
			// 字符串类型
			if (tempObj != null && tempObj.getClass() == String.class) {
				String tempStr = tempObj.toString();
				// 替换&为&amp
				tempStr = tempStr.replace("&", "&amp;");
				// 替换<为&lt
				tempStr = tempStr.replace("<", "&lt;");
				// 替换>为&gt
				tempStr = tempStr.replace(">", "&gt;");
				// 替换'为&apos
				tempStr = tempStr.replace("'", "&apos;");
				// 替换"为&quot
				tempStr = tempStr.replace("\"", "&quot;");

				// 重新放回
				dataMap.put(tempKey, tempStr);
			}
		}
	}
}
