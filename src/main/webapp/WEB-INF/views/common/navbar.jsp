<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark mb-3">
	<div class="container">
		<ul class="navbar-nav me-auto">
			<li class="nav-item"><a class="nav-link ${menu eq 'home' ? 'active' : '' }" href="/home">샘플 애플리케이션</a></li>
			<sec:authorize access="hasAnyRole('ROLE_GUEST', 'ROLE_USER')">
				<li class="nav-item"><a class="nav-link ${menu eq 'post' ? 'active' : '' }" href="/post/list">게시판</a></li>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle ${menu eq 'admin' ? 'active' : '' }" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
						관리자
					</a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="/admin/users">사용자 관리</a></li>
						<li><a class="dropdown-item" href="/admin/posts">게시글 관리</a></li>
					</ul>
				</li>
			</sec:authorize>
		</ul>
		<sec:authorize access="isAuthenticated()">
			<span class="navbar-text"><strong class="text-white"><sec:authentication property="principal.name"/></strong>님 환영합니다.</span>
		</sec:authorize>
		<ul class="navbar-nav">
			<sec:authorize access="isAuthenticated()">
				<sec:authorize access="hasRole('ROLE_USER')">
					<li class="nav-item"><a class="nav-link ${menu eq 'user' ? 'active' : '' }" href="/user/info">내정보 보기</a></li>
				</sec:authorize>
				<li class="nav-item"><a class="nav-link" href="/logout" onclick="logout(event)">로그아웃</a></li>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<li class="nav-item"><a class="nav-link ${menu eq 'login' ? 'active' : '' }" href="/login">로그인</a></li>
				<li class="nav-item"><a class="nav-link ${menu eq 'register' ? 'active' : '' }" href="/register">회원가입</a></li>				
			</sec:authorize>
		</ul>
	</div>
</nav>
<form id="form-logout" method="post" action="logout">
	<sec:csrfInput />
</form>
<script>
	function logout(event) {
		event.preventDefault();
		document.getElementById("form-logout")
	}
</script>