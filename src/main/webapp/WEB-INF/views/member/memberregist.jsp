<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="form"
uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>회원가입</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/member/memberregist.js"></script>
  </head>
  <body>
    <h1>회원가입</h1>
    <form:form modelAttribute="memberRegistVO" method="post" action="/member/regist">
      <div class="grid">
        <label for="email">이메일</label>
        <div>
          <form:errors path="email" element="div" cssClass="errors" />
          <input
            type="email"
            id="email"
            name="email"
            value="${memberRegistVO.email}"
          />
        </div>
        <label for="name">이름</label>
        <div>
          <form:errors path="name" element="div" cssClass="errors" />
          <input type="text" id="name" name="name" value="${memberRegistVO.name}" />
        </div>
        <label for="password">비밀번호</label>
        <div>
          <form:errors path="password" element="div" cssClass="errors" />
          <input
            type="password"
            id="password"
            name="password"
            value="${memberRegistVO.password}"
          />
        </div>
        <label for="confirmPassword">비밀번호 확인</label>
        <div>
          <form:errors path="confirmPassword" element="div" cssClass="errors" />
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value="${memberRegistVO.confirmPassword}"
          />
        </div>
        <div class="btn-group">
          <div class="right-align">
            <input type="submit" value="등록" />
          </div>
        </div>
      </div>
    </form:form>
  </body>
</html>
