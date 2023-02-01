<%@ page contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>애플리케이션</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
</head>
<body>
<c:set var="menu" value="admin" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">사용자 관리</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-12">
			<table class="table">
				<colgroup>
					<col width="5%">
					<col width="15%">
					<col width="10%">
					<col width="20%">
					<col width="15%">
					<col width="10%">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th>순번</th>
						<th>아이디</th>
						<th>이름</th>
						<th>이메일</th>
						<th>연락처</th>
						<th>탈퇴</th>
						<th>가입일</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${users }" varStatus="status">
						<tr>
							<td>${status.count }</td>
							<td>${user.id }</td>
							<td>${user.name }</td>
							<td>${user.email }</td>
							<td>${user.tel }</td>
							<td>${user.deleted }</td>
							<td><fmt:formatDate value="${user.createdDate }" /></td>
							<td>
								<c:choose>
									<c:when test="${user.deleted eq 'Y' }">
										<a href="restore?userId=${user.id }" class="btn btn-outline-success btn-sm">복구</a>
									</c:when>
									<c:otherwise>
										<a href="delete?userId=${user.id }" class="btn btn-outline-danger btn-sm">탈퇴</a>
									</c:otherwise>
								</c:choose>
								<a href="chang-role?userId=${user.id }" class="btn btn-outline-primary btn-sm">권한변경</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</body>
</html>