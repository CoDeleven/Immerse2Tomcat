《深入剖析Tomcat》第十章

# 概要
有些WEB应用程序的一些内容是受限的，只有授权的用户在提供了正确的信息后才能查看它们。本章将学习Web容器是如何支持安全限制功能的。

Servlet容器是通过一个叫验证器的阀来支持安全限制的，在调用Wrapper阀之前，会先调用验证器阀对当前用户进行验证。验证通过后继续访问请求的Servlet，如果未通过则直接返回。

# 概念
## 领域
领域对象用来对用户输入的用户名和密码进行有效性判断，通常这个对象会**保存所有用户的数据信息**，比如可以通过tomcat-user.xml
抑或是关系数据库，然后在authenticate()方法内进行判断。

## Principal（主角、本金、首长）
存有用户名、密码、角色的对象

## LoginConfig
保存有领域对象名、所使用的身份验证方法

## Authenticator
一个阀，当请求进来，通过该层时，会调用Context获取Realm并进行authenticate()，如果Realm校验成功，则获取到Principal对象并设置到对应的Session中去，最后将请求继续转发给对应的Servlet

# 流程
![](https://blog-1252749790.file.myqcloud.com/tomcat/SecurityFlowchart.png)