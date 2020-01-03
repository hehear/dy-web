package com.dy.u.report.service;

import com.dy.u.report.model.PdfQueryVO;

/**
 * @Description pdf生成service
 * @author dxy
 * @date 2019-12-26
 */
public interface IPdfGenService {

    /**
     * 根据freemarker模板生成pdf
     * @return
     * @throws Exception
     */
    public int genPdfFileByFreeMarkerTemplate(PdfQueryVO vo) throws Exception;

    /**
     * 根据pdf模板生成pdf
     * @return
     * @throws Exception
     */
    public int genPdfFileByPdfTemplate(PdfQueryVO vo) throws Exception;

    /**
     * 根据html内容生成pdf
     * @param vo
     */
    public String genPdfFileByHtmlContent(PdfQueryVO vo) throws Exception;
}
