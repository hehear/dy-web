package com.dy.u.report.rest;

import com.dy.s.basic.common.CommonRest;
import com.dy.s.basic.common.SimpleMessage;
import com.dy.s.basic.common.SimpleObjectMessage;
import com.dy.s.basic.util.FileUtil;
import com.dy.u.report.model.PdfQueryVO;
import com.dy.u.report.service.IPdfGenService;
import com.dy.u.report.util.ReportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description pdf生成rest
 * @author dxy
 * @date 2019-12-04
 */
@RestController("/report/pdf")
@Api("pdf")
public class PdfGenRest extends CommonRest<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenRest.class);

    @Autowired
    IPdfGenService pdfGenService;

    /**
     * 根据freemarker模板生成pdf
     * @return
     */
    @RequestMapping(value="/genPdfByFtl",method = RequestMethod.GET)
    @ApiOperation("gen-pdf-ftl")
    public SimpleMessage<Object> genPdfByFtl(){

        SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

        try {

            PdfQueryVO vo = new PdfQueryVO();

            pdfGenService.genPdfFileByFreeMarkerTemplate(vo);

            } catch (Exception e) {
                e.printStackTrace();
                return error(e);
            }
        return sm;
    }

    /**
     * 根据pdf模板生成pdf
     * @return
             */
    @RequestMapping(value="/genPdfByPdf",method = RequestMethod.GET)
    @ApiOperation("gen-pdf-pdf")
    public SimpleMessage<Object> genPdfByPdf(){

        SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

        try {

            PdfQueryVO vo = new PdfQueryVO();

            pdfGenService.genPdfFileByPdfTemplate(vo);

        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
        return sm;
    }

    /**
     * 根据html生成pdf
     * @return
     */
    @RequestMapping(value="/genPdfByHtmlContent",method = RequestMethod.POST)
    @ApiOperation("gen-pdf-pdf")
    public SimpleMessage<Object> genPdfByHtmlContent(@RequestParam("htmlContent") String htmlContent){

        SimpleObjectMessage<Object> sm = new SimpleObjectMessage<Object>();

        try {

            LOGGER.info("开始根据html内容生成pdf文件，内容为："+htmlContent);
            PdfQueryVO vo = new PdfQueryVO();
            vo.setHtmlContent(htmlContent);

            String pdfPath = pdfGenService.genPdfFileByHtmlContent(vo);

            sm.setRecord(pdfPath);
            LOGGER.info("根据html内容生成pdf结束");

        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
        return sm;
    }

    /**
     * 下载pdf
     * @return
     */
    @RequestMapping(value="/downLoadPdfFile",method = RequestMethod.POST)
    @ApiOperation("gen-pdf-pdf")
    public SimpleMessage<Object> downLoadPdfFile(@RequestParam("pdfPath") String pdfPath, HttpServletResponse rep){

        SimpleObjectMessage<Object> sm = null;

        try {

            LOGGER.info("开始下载pdf文件");
            ReportUtil.sendFile(rep,pdfPath,"application/pdf","resume.pdf");
            LOGGER.info("下载pdf文件结束");

        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
        return sm;
    }

    /**
     * 下载md文件
     * @return
     */
    @RequestMapping(value="/downLoadMdFile",method = RequestMethod.POST)
    @ApiOperation("gen-pdf-pdf")
    public SimpleMessage<Object> downLoadMdFile(@RequestParam("mdContent") String mdContent, HttpServletResponse rep){

        SimpleObjectMessage<Object> sm = null;

        try {

            LOGGER.info("下载md文件开始");
            String mdPath = FileUtil.generateMdFile(mdContent);

            ReportUtil.sendFile(rep,mdPath,"application/octet-stream","resume.md");
            LOGGER.info("下载md文件结束");

        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
        return sm;
    }


}
