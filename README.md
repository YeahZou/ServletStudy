# Tomcat的目录
在Tomcat中，一个webapp，也就是Tomcat server.xml 中的web context，由一系列资源文件（如HTML、CSS、JavaScript、images、程序和库）构成。Java webapp有一个用来存储各类资源的标准化目录结构。假设我们的应用名为```helloservlet```,其目录结构如下图所示(CATALINA_HOME为Tomcat的安装目录)：  
![java webapp 目录结构](https://note.youdao.com/yws/api/personal/file/WEB97cab70f596f5ce34c11b5f46342ea90?method=getImage&version=1774&cstk=4qWr_Bwl)  
资源的存放位置必须和目录对应：  
- <CATALINA_HOME>\webapps\helloservlet：这是 web context “helloservlet”的 context root。除了固定的```WEB-INF```目录和```MATE-INF```目录客户端不可访问外，其他文件/目录都可访问。根据需要我们可以创建自己的目录。  
- <HELLOSERVLET>\WEB-INF：该目录客户端不可直接访问，用来存放应用相关的配置文件（如web.xml）、编译好的class文件目录（classes）、源代码文件目录（src）以及库文件目录（lib）。  
- <HELLOSERVLET>\MATE-INF：该目录客户端也不能直接访问，用于存放和具体的服务器（Tomcat、Glassfish...）相关的资源文件和配置文件(如context.xml)。对应的，WEB-INF中的资源文件和配置文件和 webapp相关，和服务器无关。  
# 用记事本创建webapp
## 创建 ```Hello Servlet.java``` Servlet
写一个“HelloServlet.java”servlet，它将被编译为“HelloServlet.class”，客户端通过访问URL```http://localhost:8080/helloServlet/sayHello```调用```HelloServlet.class```。  
为了能够正常发布，一个```servlet```应该被放置在一个Java包内。这里我把它放在```mypkg```包内，代码如下：  
```java
// <CATALINA_HOME>\webapps\helloservlet\WEB-INF\src\mypkg\HelloServlet.java
package mypkg;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// set response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // write response message into network socket
        PrintWriter out = response.getWriter();
        try {
           out.println("<!DOCTYPE html>");
           out.println("<html><head>");
           out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
           out.println("<title>Hello, World</title></head>");
           out.println("<body>");
           out.println("<h1>Hello, world!</h1>");  // says Hello
           // Echo client's request information
           out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
           out.println("<p>Protocol: " + request.getProtocol() + "</p>");
           out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
           out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
           // Generate a random number upon each request
           out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
           out.println("</body>");
           out.println("</html>");
        } catch (Exception e) {
            
        } finally {
            out.close();
        }
    }
}
```
编译代码：  
```
> cd <CATALINA_HOME>\webapps\helloservlet\WEB-INF
> javac -d classes src\mypkg\HelloServlet.java
```
** 说明 **
1. 编译这段代码需要```Servler API```库的支持，它不包含在JDK 或 Java SE内（但是Java EE的一部分），在Tomcat 的```<CATALINA_HOME>/lib```目录下提供了包含该API的包```servlet-api.jar```。如果编译不通过，可以将该jar包复制一份到```<JAVA_HOME>/jre/lib/ext```(jdk扩展目录)目录，也可以将该jar文件添加到```CLASSPATH```中。  
2. 通过给定```-d```选项，指定编译后的class文件的存放位置  
3. 我们没有从最原始的```Servlet```接口开始写代码，而是通过其子类```javax.servlet.http.HttpServlet```创建一个servlet。  
4. 整个处理过程是，客户端发起一个HTTP请求，服务器将该请求路由给对应的servlet处理，servlet返回响应报文给客户端。  
5. HTTP请求可以是```GET```或```POST```请求方法，servlet将使用与之对应的```doGet()```和```doPost()```处理，当然也可以忽略请求方式，统一处理，这就要在```service()```方法中处理。  
6. ```HttpServletRequest```对象中包含了客户端发送过来的HTTP请求头和表单数据，```HttpServletResponse```对象可用来设置HTTP响应头（response headers）和响应报文体（response message body）。  
7. 客户端需要知道接收到的数据类型，以决定如何显示/处理，使用```response.setContentType(MIMEType)```方法设置响应的MIME类型。  
## 配置应用发布描述文件（Application Deployment Descriptor）web.xml
web用户通过向指定的URL发起请求来调用 servlet，但是servlet运行在web服务器内，URL和servlet是怎么对应上的呢？这就是````web.xml```配置文件要做的事情之一：建立URL和servlet的对应关系。  
```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app version="3.0"
  xmlns="http://java.sun.com/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
 
  <!-- To save as <CATALINA_HOME>\webapps\helloservlet\WEB-INF\web.xml -->
 
   <servlet>
      <servlet-name>HelloWorldServlet</servlet-name>
      <servlet-class>mypkg.HelloServlet</servlet-class>
   </servlet>
 
   <!-- 所有 <servlet> 元素都必须放在一起，并置于 <servlet-mapping> 元素前 -->
   <servlet-mapping>
      <servlet-name>HelloWorldServlet</servlet-name>
      <url-pattern>/sayhello</url-pattern>
   </servlet-mapping>
</web-app>
```
以上配置定义了一个叫```HelloServlet```的servlet，它在```mypkg.HelloServlet.class```中被实现，与URL ```/sayhello```建立映射关系。“/”为webapp “helloservlet”的根路径（context root）。这样一来，就可以通过URL ```http://localhost:8080/helloservlet/sayhello``` 访问servlet ```HelloServlet```。注意```webapp```下的目录名称叫什么，url前缀部分就是什么。  
![URL-Servlet的映射关系](https://note.youdao.com/yws/api/personal/file/WEBbf2c5b3b25ce6f734352fd21f37e4de4?method=getImage&version=1979&cstk=4qWr_Bwl)
## 运行 HelloServlet Servlet
启动 Tomcat，只要看到Tomcat 控制台输出以下信息，则说明发布成功。  
这种发布方式和使用 war 包发布不同，这种方式发布不需要配置```server.xml```文件。
