
<%@ page import="com.simplicityitself.plugin.test.UserName" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userName.label', default: 'UserName')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'userName.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="dateJoined" title="${message(code: 'userName.dateJoined.label', default: 'Date Joined')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'userName.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'userName.password.label', default: 'Password')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userNameInstanceList}" status="i" var="userNameInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${userNameInstance.id}">${fieldValue(bean: userNameInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${userNameInstance.dateJoined}" /></td>
                        
                            <td>${fieldValue(bean: userNameInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: userNameInstance, field: "password")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userNameInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
