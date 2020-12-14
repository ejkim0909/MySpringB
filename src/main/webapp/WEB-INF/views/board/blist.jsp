<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<table>
		<tr>
			<td align="right" colspan="5">
				<input type="button" value="전체목록" onclick="window.location='bList.do'"> 
				<input type="button" value="글쓰기" onclick="window.location='writeForm.do'">
			</td>
		</tr>
		<tr bgcolor="#FFD1B7">
			<td align="center" width="60">번호</td>
			<td align="center" width="380">제목</td>
			<td align="center" width="100">작성자</td>
			<td align="center" width="100">작성일</td>
			<td align="center" width="60">조회수</td>
		</tr>
		<!-- 글이 없을 경우 -->
		<!-- 글이 있을 경우 -->
		<c:forEach var="vo" items="${list}" varStatus="status">
			<tr>
				<td align="center">${status.count}</td>
				<td align="left"><a	href="bDetail.do?board_num=${vo.board_num }">&nbsp;${vo.board_title}</a></td>
				<td align="center">${vo.board_writer}</td>
				<td align="center">${vo.regDate}</td>
				<td align="center">${vo.read_count}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>