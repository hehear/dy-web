package com.dy.u.report.rest;

import com.dy.s.basic.common.CommonRest;
import com.dy.s.basic.common.SimpleMessage;
import com.dy.s.basic.common.SimpleObjectMessage;
import com.dy.u.report.model.PdfQueryVO;
import com.dy.u.report.service.IPdfGenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description pdf生成rest
 * @author dxy
 * @date 2019-12-04
 */
@RestController
@Api("pdf")
public class PdfGenRest extends CommonRest<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenRest.class);

    @Autowired
    IPdfGenService pdfGenService;

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
}
