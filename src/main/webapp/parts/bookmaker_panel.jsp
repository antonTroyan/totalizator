<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.role == 'BOOKMAKER'}">
    <div class="admin-panel">
        <a href="<c:url value="main?command=showAddEventPage" />">
            <fmt:message bundle="${loc}" key="link.setWinning" />
        </a>
    </div>
</c:if>
