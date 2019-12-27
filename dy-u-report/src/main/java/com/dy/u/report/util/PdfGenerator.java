package com.dy.u.report.util;

import com.dy.s.basic.util.FileUtil;
import com.itextpdf.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Description 采用IText将html格式的文件，渲染成PDF的处理类
 * @author dxy
 * @date 2019-12-04
 */
@Component
public class PdfGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerator.class);

    //@Value("${report.font.path}")
	private static String fontPath="/Users/runningcoder/Desktop/学习/pdf/font";
    
	private static String staticFontPath;
    @PostConstruct
    public void init(){
        PdfGenerator.staticFontPath = this.fontPath;
    }

    public static ITextRenderer getRenderer(){
        ITextRenderer renderer = new ITextRenderer();
        List<String> fonts = FileUtil.getAllFileNames(staticFontPath);
        ITextFontResolver fontResolver = renderer.getFontResolver();
        try{
            for(String font:fonts) {
                fontResolver.addFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            }
        } catch (Exception e) {
        	LOGGER.warn(e.getMessage());
        	LOGGER.warn("初始化IText环境失败");
        }
        return renderer;
    }
    /**
     * 根据html文件生成pdf文件
     *
     * @param htmlInput
     *          html的文件位置
     * @param pdfOutput
     *          pdf输出路径
     */

    public  static int generate(String htmlInput, String pdfOutput, ITextRenderer iTextRenderer){
        File pdf = new File(pdfOutput);
        OutputStream out = null;
        try {
            out = new FileOutputStream(pdf);
            String htmlURL = new File(htmlInput).toURI().toURL().toString();
            iTextRenderer.setDocument(htmlURL);
            iTextRenderer.layout();
            iTextRenderer.createPDF(out);
            out.flush();
            out.close();
            return 0;
        } catch ( Exception e) {
        	LOGGER.warn(e.getMessage());
        	LOGGER.warn("根据HTML渲染PDF失败");
        }
        return 1;
    }
}
