<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SamrtWeb 로그인 화면</title>
<link href="/include/css/common.css" rel="stylesheet" type="text/css" />
<link href="/include/css/contents.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="/include/js/smartweb_util.js"></script>
<script language="javascript" type="text/javascript" src="/include/js/jquery-1.6.4.js"></script>
<script language="javascript" type="text/javascript">
function login(){
	if(loginCheck()){
		loginProc('/login/loginProc.do');
	}
}

function loginCheck(){	
	//입력값 체크
	if (!chkSubmit($('#ADM_ID'),"아이디를"))	return;
	else if (!chkSubmit($('#ADM_PWD'),"비밀번호를"))	return;
	else return true;
}

function loginProc(url){
	$.ajax({
		url : url,
		cache : false,
		type : "POST",
		data : $("#loginFrm").serialize(),
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		
		beforeSend : function(){},
		
		error : function(){
			alert('시스템 오류 입니다. 관리자에게 문의 하세요');
		},
		
		success : function(response){
			/* 응답코드 정의
			 * A : 로그인 성공
			 * B : 사용중지 계정
			 * C : 로그인 실패
			 * D : 로그인 성공(미사용계정)
			 * E : 계정상태 오류
			 * F : 입력값 오류
			 */
			//alert(response);
			
			switch(response){
				case 'A': location.href='/admin/admin_list.do'; break;
				case 'B': alert('사용중지된 계정입니다.'); break;
				case 'C': alert('아이디가 없거나 비밀번호가 틀렸습니다.\n5회 실패시 계정이 사용중지 됩니다.'); break;
				case 'D': location.href='/admin/admin_list.do'; break;	//비밀번호 변경은 popup으로 header에서 체크해 띄움
				case 'E': alert('계정에 문제가 있어 로그인 할 수가 없습니다. 관리자에게 문의 하세요'); break;
				case 'F': alert('아이디와 비밀번호를 입력하세요.'); break;
			}
		}
	});	
}

</script>
</head>
<body>
<body class="bg_no">
	<form id="loginFrm" onsubmit="return false;">
	<div id="wrap" class="login">
		<div class="loginBox loginBox_admin">
			<ul>
				<li>
					<span class="mr10"><img src="../images/login/logtxt_memid.gif" alt="아이디" /></span><input type="text" id="ADM_ID" name="ADM_ID" class="inputidpw" />
				</li>
				<li class="mt5">
					<span class="mr3"><img src="../images/login/logtxt_pw.gif" alt="비밀번호" /></span><input type="password" id="ADM_PWD" name="ADM_PWD" class="inputidpw" onkeydown="if(keyDown(event)) login();"/></li>
				<li class="btn"><a href="#" onclick="login();"><img src="../images/login/btn_login.jpg" alt="로그인" /></a></li>
				<!--li class="btn2">
					<p class="other">아이디/비밀번호를 잊으셨습니까?
					<a href="#" onclick="goIdPwdFind()"><img src="../images/img/btn_idpw.jpg" alt="아이디/비밀번호 찾기" class="btn_idpw"/></a></p>
				</li-->
				<li><p class="mt10 b bg_no" style="color: #888; font-size: 11px;">고객센터 전화번호 : 0000-0000 / 상담시간 : 09:00 ~ 18:00</p></li>
			</ul>
		</div>
	</div>
	</form>
</body>
</html>