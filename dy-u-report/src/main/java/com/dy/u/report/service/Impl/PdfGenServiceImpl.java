package com.dy.u.report.service.Impl;

import com.dy.u.report.model.PdfQueryVO;
import com.dy.u.report.service.IPdfGenService;
import com.dy.u.report.util.ConcurrentPdfGenerator;
import com.dy.u.report.util.ReportUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description pdf生成service实现类
 * @author dxy
 * @date 2019-12-04
 *
 */
@Service("pdfGenService")
public class PdfGenServiceImpl implements IPdfGenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenServiceImpl.class);

    private final static String pdfFilePath = "/Users/runningcoder/Desktop/学习/pdf";
    private final static String ftlTemplateName = "gen_pdf_template";
    private final static String ftlTemplateCssName = "gen_pdf_css";

    /**
     * 根据freemarker模板生成pdf
     * @return
     * @throws Exception
     */
    @Override
    public int genPdfFileByFreeMarkerTemplate(PdfQueryVO vo) throws Exception {

        //生成前删除数据

        //生成pdf文件
        int count = genPdfFileByFtlTemplate(1);


        return count;
    }

    private int genPdfFileByFtlTemplate(int genPdfCount) {

        //个数
        final AtomicInteger pdfCount = new AtomicInteger(0);
        //开始时间
        long startTime = System.currentTimeMillis();

        //线程数
        int threadCount = 8;
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        //所有线程任务的个数
        List<List<String>> lists = new ArrayList<>(threadCount);

        int countAll = genPdfCount;

        //每个线程分得个数
        final int preCount = countAll / threadCount+(countAll % threadCount == 0 ? 0 : 1);

        for (int i = 0;i<threadCount;i++){
            //每个list
            List<String> list = new ArrayList<>(preCount);

            for (int j = i * preCount; j < (i+1) * preCount; j++) {
                list.add("111");
            }
            lists.add(list);
        }

        List<Future<?>> futures = new ArrayList<>(threadCount);

        for (final List<String> list: lists) {

            Runnable task = () -> {
                try {
                    //字体
                    List<String> fontPathList = ReportUtil.getFontPathList();

                    for (String data:list) {

                        Map<String, Object> dataMap = new HashMap<>();

                        dataMap.put("paraContent","testdata");

                        String pdfFileName = pdfFilePath+"/template.pdf";

                        ITextRenderer textRenderer = ConcurrentPdfGenerator.newTextRenderer();

                        ConcurrentPdfGenerator.generate(textRenderer,ftlTemplateName,ftlTemplateCssName,dataMap,pdfFileName,fontPathList);

                    }

                }catch (Exception e){
                    LOGGER.error("生成pdf出错！",e);
                }
            };

            futures.add(executor.submit(task));
        }

        //主线程等待pdf线程
        executor.shutdown();

        //线程等待，判断每个线程生成完成
        for (Future<?> future:futures) {
            try {
                future.get();
            }catch (Exception e){
                executor.shutdownNow();
            }
        }


        return pdfCount.get();

    }

    /**
     * 根据pdf模板生成pdf文件
     * @return
     * @throws Exception
     */
    @Override
    public int genPdfFileByPdfTemplate(PdfQueryVO vo) throws Exception {

        // 模板路径
        String templatePath = "/Users/runningcoder/git/dy-web/dy-web/target/template/template.pdf";
        // 生成的新文件路径
        String newPDFPath = pdfFilePath+"/1.pdf";

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            //字体
            BaseFont bf = BaseFont.createFont("/Users/runningcoder/Desktop/学习/pdf/font/STSong-Light.ttf" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font FontChinese = new Font(bf, 5, Font.NORMAL);
            // 输出流
            out = new FileOutputStream(newPDFPath);
            // 读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);

            //数据赋值
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            form.addSubstitutionFont(bf);
            form.setField("Test1","test1");
            form.setField("Test2","test2");

            //图片类的内容处理
            String key = "Test3";
            String imgpath = "/Users/runningcoder/Desktop/学习/pdf/1.jpg";
            int pageNo = form.getFieldPositions(key).get(0).page;
            Rectangle signRect = form.getFieldPositions(key).get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            //根据路径读取图片
            Image image = Image.getInstance(imgpath);
            //获取图片页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            //图片大小自适应
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            //添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);

            // 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            Font font = new Font(bf, 32);
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (Exception e) {
            LOGGER.error("生成pdf出错！",e);
        }
        return 1;
    }
}
