package com.dy.u.report.model;

import java.io.Serializable;

/**
 * @Description pdf的查询VO，用于封装查询数据
 * @autho dxy
 * @date 2019-12-26
 */
public class PdfQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //html内容
    private String htmlContent;

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
