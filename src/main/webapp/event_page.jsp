<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="<c:url value="styles/styles.css" />" rel="stylesheet"/>
    <link href="<c:url value="styles/event.css" />" rel="stylesheet"/>


    <title>Totalizator</title>
</head>
<body>
<div class="container">
    <%@ include file="parts/header.jsp" %>
    <div class="main">
        <%@ include file="parts/left_menu.jsp" %>
        <div class="center-part">
            <div class="event-block">
                <div class="event-header">
                    <div class="event-info">
                        <h5 class="event-name"><c:out value="${event.eventName}"/></h5>
                        <p class="event-league">${event.eventLeague}</p>
                        <time>Дата начала: ${event.eventDate} ${event.eventTime}</time>
                    </div>
                    <c:if test="${event.status eq 'FINISHED'}">
                        <div class="event-result">
                            <p>${event.result.winnerName} (${event.result.winnerScore}) :
                                (${event.result.loserScore}) ${event.result.loserName}</p>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'ADMINISTRATOR'}">
                        <c:if test="${event.canAddResult}">
                            <div class="event-result">
                                <a class="add-result-link"
                                   href="<c:url value="main?command=fillRandomResult&eventId=${event.eventId}"/>">
                                    <fmt:message bundle="${loc}" key="link.addEventResult"/>
                                </a>
                            </div>
                        </c:if>
                    </c:if>
                </div>
                <c:if test="${sessionScope.role eq 'BOOKMAKER'}">
                    <c:if test="${event.status eq 'POSTED'}">
                        <c:if test="${event.coefficient != 0.0}">
                            <label><fmt:message bundle="${loc}" key="label.coefficient"/></label>
                            <p class="label">${event.coefficient}</p>
                        </c:if>
                        <c:if test="${event.coefficient == 0.0}">
                        <form class="form" method="post" action="main?command=setCoefficient&eventId=${event.eventId}">
                        <div class="input-div">
                            <label><fmt:message bundle="${loc}" key="label.coefficient"/></label>
                            <input name="coefficient" pattern="[0-9]{1,2}\.[0-9]{1,2}"/>
                        </div>
                        <div class="btn-container">
                            <button class="button" type="submit" >
                                <fmt:message bundle="${loc}" key="link.set-coefficient"/>
                            </button>
                        </div>
                        </form>
                        </c:if>
                    </c:if>
                </c:if>
                <c:if test="${!empty event.liveTranslationLink}">
                    <div class="event-live">
                        <iframe width="640" height="480" src="${event.liveTranslationLink}" frameborder="0"
                                allowfullscreen>
                        </iframe>
                    </div>
                </c:if>
                <div class="members-div">
                    <h4><fmt:message bundle="${loc}" key="label.members"/>:</h4>
                    <c:forEach var="member" items="${event.members}">
                        <ul class="member">
                            <li>${member.name}</li>
                        </ul>
                    </c:forEach>
                </div>
                <div class="input-div">
                <c:choose>
                    <c:when test="${!empty sessionScope.username}">
                        <c:if test="${event.canMakeRate}">
                            <a class="button" href="<c:url value="main?command=showMakeRatePage&eventId=${event.eventId}"/>">
                                <fmt:message bundle="${loc}" key="link.make-rate"/>
                            </a>
                        </c:if>
                    </c:when>
                    <c:when test="${empty sessionScope.username}">
                        <div class="register-warn-div">
                            <p><fmt:message bundle="${loc}" key="link.log-in-to-make-rate"/></p>
                        </div>
                    </c:when>
                </c:choose>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="parts/footer.jsp" %>
</div>
</body>
</html>