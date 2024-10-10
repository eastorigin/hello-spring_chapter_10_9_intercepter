<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>게시글 내용 조회</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
  </head>
  <body>
    <div class="membermenu">
      <jsp:include page="../member/membermenu.jsp"></jsp:include>
    </div>
    <h1>게시글 작성</h1>
    <label for="subject">제목</label>
    <div>${boardVO.subject}</div>
    <br />
    <label for="email">이메일</label>
    <div>${boardVO.memberVO.name} (${boardVO.memberVO.name})</div>
    <br />
    <label for="viewCnt">조회수</label>
    <div>${boardVO.viewCnt}</div>
    <br />
    <label for="originFileName">첨부파일</label>
    <div>
      <a href="/board/file/download/${boardVO.id}">${boardVO.originFileName}</a>
    </div>
    <br />
    <label for="crtDt">등록일</label>
    <div>${boardVO.crtDt}</div>
    <br />
    <label for="mdfyDt">수정일</label>
    <div>${boardVO.mdfyDt}</div>
    <br />
    <label for="content">내용</label>
    <div>${boardVO.content}</div>
    <br />
    <div class="btn-group">
      <div class="right-align">
        <a href="/board/modify/${boardVO.id}">수정</a>
        <a href="/board/delete/${boardVO.id}">삭제</a>
      </div>
    </div>
  </body>
</html>
