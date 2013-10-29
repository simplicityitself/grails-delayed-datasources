

<%@ page import="com.simplicityitself.plugin.test.UserName" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'userName.label', default: 'UserName')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userNameInstance}">
            <div class="errors">
                <g:renderErrors bean="${userNameInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateJoined"><g:message code="userName.dateJoined.label" default="Date Joined" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userNameInstance, field: 'dateJoined', 'errors')}">
                                    <g:datePicker name="dateJoined" precision="day" value="${userNameInstance?.dateJoined}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="userName.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userNameInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${userNameInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="userName.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userNameInstance, field: 'password', 'errors')}">
                                    <g:textField name="password" value="${userNameInstance?.password}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
