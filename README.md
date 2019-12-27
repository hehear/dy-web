# dy-web

- 简介：一套java web整合项目，
- 框架：springboot、springmvc、mybatis、springsecurity等框架整合
- 数据库：mysql、redis缓存、mongo
- 功能：实现了java web的基础功能。用户注册登陆、权限、文件管理，redis缓存管理、报表处理等。

## dy-s-basic

公用基础模块，存储多个模块公用的工具类、枚举、model、异常等信息

## dy-u-file

文件处理模块，负责处理文件的上传下载，采用存储mongodb的形式。

## dy-u-report

报表处理模块，负责处理word、excel、pdf等报表，目前只实现了itext+freemarker+flying saucer和itext+pdf模板 生成pdf，其他功能后续开发。

## dy-u-security

代码开发整理中。。。
