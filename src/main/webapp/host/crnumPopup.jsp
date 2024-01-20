<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>crnumPopup.jsp</title>
</head>
<body>

    <script>
        function validateCRNum(crnum) {
            var checkID = [1, 3, 7, 1, 3, 7, 1, 3, 5, 1];
            var bizID = crnum.replace(/-/gi, '');
            var chkSum = 0, c2, remainder;

            for (var i = 0; i <= 7; i++) {
                chkSum += checkID[i] * parseInt(bizID.charAt(i));
            }

            c2 = "0" + (checkID[8] * parseInt(bizID.charAt(8)));
            c2 = c2.substring(c2.length - 2, c2.length);
            chkSum += Math.floor(parseInt(c2.charAt(0))) + Math.floor(parseInt(c2.charAt(1)));
            remainder = (10 - (chkSum % 10)) % 10;

            return parseInt(bizID.charAt(9)) === remainder;
        }

        function selectCRNum() {
            var inputCRNum = document.getElementById('crnumInput').value;
            var resultMessage = document.getElementById('resultMessage');

            if (validateCRNum(inputCRNum)) {
                resultMessage.textContent = "유효한 사업자번호입니다.";
                if (window.opener && !window.opener.closed) {
                    window.opener.crnumCallBack(inputCRNum);
                }
                window.close();
            } else {
                resultMessage.textContent = "유효하지 않은 사업자번호입니다.";
            }
        }
    </script>

    <h2 style="text-align: center; font-weight: bold; font-family: 'Arial'">사업자번호 유효성 검사</h2>
    <div style="display: flex; justify-content: center; align-items: center;">
    <input type="text" id="crnumInput" placeholder="'-' 제외 10자리 숫자" maxlength="10" style="text-align: center;">
    <button onclick="selectCRNum()">조회</button></div>
    <p id="resultMessage"></p>

</body>
</html>
