<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<title>애플리케이션</title>
</head>
<body>
<c:set var="menu" value="register" />
<%@ include file="common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">회원가입</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-12">
			<p>회원가입 정보를 입력하세요</p>
			
			<form:form modelAttribute="userRegisterForm" id="form-register" class="border bg-light p-3" method="post" action="register">
				<div class="mb-3">
					<label class="form-label">접속 권한</label>
					<div>
						<div class="form-check form-check-inline">
							<form:checkbox class="form-check-input" path="roleName" value="ROLE_GUEST" />
							<label class="form-check-label">게스트</label>
						</div>
						<div class="form-check form-check-inline">
							<form:checkbox class="form-check-input" path="roleName" value="ROLE_USER" />
							<label class="form-check-label">사용자</label>
						</div>
					</div>
					<form:errors path="roleName"/>
				</div>
				<div class="mb-3">
					<label class="form-label">아이디</label>
					<form:input class="form-control form-control-sm" path="id" />
					<form:errors path="id" cssClass="text-danger"/>
				</div>
				<div class="mb-3">
					<label class="form-label">비밀번호</label>
					<form:password class="form-control form-control-sm" path="password" />
					<form:errors path="password" cssClass="text-danger"/>
				</div>
				<div class="mb-3">
					<label class="form-label">이름</label>
					<form:input class="form-control form-control-sm" path="name" />
					<form:errors path="name" cssClass="text-danger"/>
				</div>
				<div class="mb-3">
					<label class="form-label">이메일</label>
					<form:input class="form-control form-control-sm" path="email" />
					<form:errors path="email" cssClass="text-danger"/>
				</div>
				<div class="mb-3">
					<label class="form-label">전화번호</label>
					<form:input class="form-control form-control-sm" path="tel" />
					<form:errors path="tel" cssClass="text-danger"/>
				</div>
				<div class="text-end">
					<a href="/home" class="btn btn-secondary btn-sm">취소</a>
					<button type="submit" class="btn btn-primary btn-sm">가입</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>