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
<c:set var="menu" value="login" />
<%@ include file="common/navbar.jsp" %>
<div class="container">
	<div class="row mb-3">
		<div class="col-12">
			<h1 class="border bg-light p-2 fs-4">로그인</h1>
		</div>
	</div>
	<div class="row mb-3">
		<div class="col-12">
			<p>아이디와 비밀번호를 입력하고 로그인 버튼을 클릭하세요</p>
			<form id="form-register" class="border bg-light p-3" method="post" action="login">
				<div class="mb-3">
					<label class="form-label">아이디</label>
					<input type="text" class="form-control form-control-sm" name="id" />
				</div>
				<div class="mb-3">
					<label class="form-label">비밀번호</label>
					<input type="password" class="form-control form-control-sm" name="password" />
				</div>
				<div class="text-end">
					<a href="/home" class="btn btn-secondary btn-sm">취소</a>
					<button type="submit" class="btn btn-primary btn-sm">로그인</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#form-register").submit(function() {
		let id = $("#form-register :input[name=id]").val();
		let password = $("#form-register :input[name=password]").val();
		
		if (id == "") {
			alert("아이디는 필수입력값입니다.");
			return false;
		}
		if (password == "") {
			alert("비밀번호는 필수입력값입니다.");
			return false;
		}
		return true;
	});
})
</script>
</body>
</html>