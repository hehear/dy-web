package com.dy.u.report.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description excel生成工具类
 * @author dxy
 * @date 2019-12-04
 */
public class ExcelGenerator {
	
	private ExcelGenerator(){}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelGenerator.class);

	/**
	 * 功能：基于模板的方式生成excel表格,并保存到本地
	 * 
	 * @param templateName
	 *            ftl模板名称
	 * @param excelOutputPath
	 *            excel生成路径
	 * @param dataMap
	 *            ftl数据字典
	 * @param encoding
	 *            期望的字符编码，一般为UTF-8
	 */
	public static void createExcel(String templateName, String excelOutputPath,
			Map<String, Object> dataMap, String encoding) {

		Configuration config = FreemarkerConfiguration.getConfiguration();
		Template template;

		try {
			template = config.getTemplate(templateName);
			File outFile = new File(excelOutputPath);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), encoding));

			template.process(dataMap, out);

			out.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (TemplateException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

	/**
	 * 功能：生成excel表格，并写入流中，用于客户端到处excel
	 * 
	 * @param templateName
	 *            ftl模板名称
	 * @param
	 *            outputStream excel输出流
	 * @param dataMap
	 *            ftl数据字典
	 * @param encoding
	 *            期望的字符编码，一般为UTF-8
	 */
	public static OutputStream createExcel(String templateName,
			OutputStream outputStream, Map<String, Object> dataMap,
			String encoding) {

		Configuration config = FreemarkerConfiguration.getConfiguration();
		Template template;

		try {
			template = config.getTemplate(templateName);

			Writer out = new BufferedWriter(new OutputStreamWriter(
					outputStream, encoding));

			template.process(dataMap, out);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (TemplateException e) {
			LOGGER.error(e.getMessage(),e);
		}

		return outputStream;
	}
}