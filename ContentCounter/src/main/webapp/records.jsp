<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Content Counter | Sun Yunmiao</title>
    <%--<script type="text/javascript">--%>
    <%--function countContent() {--%>
    <%--//提交form--%>
    <%--document.formRecords.action = "${pageContext.request.contextPath }/query.action";--%>
    <%--document.formRecords.submit();--%>
    <%--}--%>
    <%--</script>--%>
</head>
<body>
<form name="formRecords" action="${pageContext.request.contextPath }/query.action" method="post">

    <table width="100%" border=1>
        <tr>
            <td>
                <input type="text" name="urlQuery" placeholder="输入文章链接"/>
                <input type="submit" value="添加"/>
            </td>
        </tr>

            <%--<td><span style="color:darkred">${message}</span></td>--%>
    </table>
    <span style="color:darkred"><%=request.getAttribute("message")%></span>

    <table width="100%" border=1>
        <tr>
            <td colspan="5" style="text-align: center">历史记录</td>
        </tr>
        <tr>
            <td>标题</td>
            <td>总字数</td>
            <td>中文字数</td>
            <td>英文字数</td>
            <td>标点符号数</td>
        </tr>
        <c:forEach items="${recordList}" var="record">
            <tr>
                <td><a href=${record.url}>${record.title}</a></td>
                <td>${record.countCharacter}</td>
                <td>${record.countChinese}</td>
                <td>${record.countEnglish}</td>
                <td>${record.countPunctuation}</td>
            </tr>
        </c:forEach>

    </table>
</form>
</body>

</html>