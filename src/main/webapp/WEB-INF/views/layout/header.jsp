<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>

</head>
<body>
	<header id="headerWrapper">
		<a href="/jsp/index" id="logoImg"> </a>
		<div class="searchContainer justify-content-center align-items-center">
			<div
				class="row height d-flex align-items-center">
				<div class="col-md-6 " id="searchFrmWrapper">
					<div class="form">
						<input type="text" class="form-control" id="searchInput">
						<i class="bi bi-search"></i>
					</div>
				</div>
			</div>
		</div>
		<div id="userWrapper">
			<a href="#" id="a">
				<i class="bi bi-person-fill"></i><span>Login</span>
			</a>
		</div>
	</header>
</body>
</html>