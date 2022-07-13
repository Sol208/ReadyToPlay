<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="/readytoplay/css/reset.css">
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">
    <link href="/readytoplay/css/user_find.css" rel="stylesheet">

<main class="text-center form-finduser">
	<form name="findPwForm" action="FindPwReg" method="post" onsubmit="return authCheck();"> <!-- onsubmit 추가 -->
        <h1 id="guide_text" class="h3 mb-3 fw-normal">회원가입 시 입력한 이메일을 입력해주세요</h1>		
        <div class="row g-3">
            <div class="col-9">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="inputEmailForm" name="email1" maxlength="30"
                        placeholder="Username" aria-label="Username"> <span class="input-group-text">@</span>
                    <select class="form-select" id="selectEmailForm" name="email2">
                        <option selected value="gmail.com">gmail.com</option>
                        <option value="naver.com">naver.com</option>
                        <option value="nate.com">nate.com</option>
                    </select>
                </div>
                <div class="invalid-feedback">이메일 인증이 필요합니다.</div>
            </div>
            <div class="col-3">
                <button class="w-100 btn btn-lg btn-primary" onclick="emailAuthentication()" type="button"
                    id="eamilAuthBtn">메일 전송</button>
            </div>
        </div>
        <div class="row g-3">
            <div class="col-9">
             <input type="text" class="form-control"
                    id="authCode" name="authCode" placeholder="" value="" required>
            </div>

            <div class="col-3">
                <button class="w-100 btn btn-lg btn-primary" onclick="authCodeCheck()" type="button"
                    id="authCodeCheckBtn">인증</button>
                <input type="hidden" name="authPass" id="authPass" value="false">
            </div>
        </div>
        <div id="find_btn">
            <button class="w-100 btn btn-lg btn-primary" type="submit">다음</button>
			<input type="hidden" name="memberkind" id="memberkind" value="${memberkind }" />        
        </div>
    </form>
</main>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script>

function authCheck(){	/* 인증 유효성검사 */
	var reg= document.getElementById("authPass").value;
	
	if(reg=="true"){
		return true
	} else if(reg=="false"){
		alert("이메일 인증을 해주세요")
		return false;
	}
}

function emailAuthentication(){
			
	  	var email = document.getElementById("inputEmailForm").value + "@" + document.getElementById("selectEmailForm").value;	  	
		var memberkind = document.getElementById("memberkind").value;
	  	var userData =  new Object();
	        	  userData.email = email;
	        	  userData.memberkind = memberkind;
	        	  userData.requestPage = "FindPw";
		$.ajax({
			url: "/readytoplay/cert/MailReg",
			data:userData,
			type:'POST',
			success: function(result){
				console.log(userData)
			},
			error:function(e){
				console.log(e);
			}
	  })
}

function pwValCheck(asValue) {
	
	var regExp = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
	return regExp.test(asValue); // 형식에 맞는 경우 true 리턴
}

function authCodeCheck(){
	var code = document.getElementById("authPass").value;
	var url = "/readytoplay/cert/authCodeCheck?inputedCode=" + document.getElementById("authCode").value;
	open(url, "confirm",
			"toolbar=no, location=no,menubar=no,scrollbars=no,resizable=no,width=300,height=200");
}
</script>