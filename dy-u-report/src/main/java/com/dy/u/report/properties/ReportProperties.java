package com.dy.u.report.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description 报表配置文件字段配置映射类
 * @author dxy
 * @date 2020-01-04
 */
@ConfigurationProperties(prefix = "report.pdf.path")
public class ReportProperties {

    // 字体路径
    private String font;

    // 模板路径
    private String template;

    // 生成路径
    private String genDir;


    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getGenDir() {
        return genDir;
    }

    public void setGenDir(String genDir) {
        this.genDir = genDir;
    }
}
