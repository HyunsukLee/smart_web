<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="map" required="true" %>
<%@ attribute name="method" required="false" %>
<%@ attribute name="action" required="false" %>
<form id="${id}" method="${method}" action="${action}">
<c:forEach var="item" items="${map}">
	<input type="hidden" name="${item.key}" value="${item.value}"/>
</c:forEach>
</form>