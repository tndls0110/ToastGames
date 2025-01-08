	var editor = new RichTextEditor("#div_editor", config);
	editor.setHTMLCode($('#content').html()); // 이게 있어야 불러온다..

function submitForm() {
    var deptSelect = document.getElementById('deptFilter'); // 부서
    var typeSelect = document.getElementById('typeFilter'); // 유형
    var deptValue = deptSelect.value;
    var typeValue = typeSelect.value;

    var titleInput = document.querySelector('input[name="board_title"]'); // 제목
    var titleValue = titleInput.value.trim();
    var contentDiv = document.getElementById('div_editor'); // 내용이 담긴 div

    // 에디터에서 내용 가져오기
    var contentValue = editor.getHTMLCode().trim(); // RichTextEditor에서 HTML 가져오기

    console.log(contentValue);
    console.log("전체 문서의 크기 : " + (contentValue.length / 1024 / 1024) + ' MB');

    // 필수 입력값 검사 및 유효성 검사
    if (deptValue === "") {
        alert("부서를 선택하세요.");
        deptSelect.focus(); // 부서 셀렉트박스에 포커스 이동
        return false; // 폼 제출을 막음
    }

    if (typeValue === "") {
        alert("유형을 선택하세요.");
        typeSelect.focus(); // 유형 셀렉트박스에 포커스 이동
        return false; // 폼 제출을 막음
    }
    
    if (titleValue === "") {
        alert("제목을 입력하세요.");
        titleInput.focus(); // 제목 입력창에 포커스 이동
        return false; // 폼 제출을 막음
    }

    if (contentValue === "") {
        alert("내용을 입력하세요.");
        contentDiv.focus(); // 에디터에 포커스 이동
        return false; // 폼 제출을 막음
    }
    
    // 내용이 100MB 이상이면 제출하지 않도록
    if (contentValue.length > 100 * 1024 * 1024) {
        alert('100MB 이상 크기는 전송이 불가능 합니다.');
        return false; // 폼 제출을 막음
    }

    // contentValue를 board_content에 설정
    document.getElementById('board_content').value = contentValue;

    // 폼 제출
    document.forms[0].submit(); // 폼 제출
}
