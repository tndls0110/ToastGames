// form_idx 가져오기
const searchParams = new URLSearchParams(window.location.search);
var form_idx = searchParams.get('form_idx');
document.getElementsByName('form_idx_modal')[0].value = form_idx;

// 에디터 설정하기
var editor = new RichTextEditor("#div_editor", configDocument);

// 양식 불러오기
previewPrint(form_idx);

function previewPrint(form_idx) {
    //console.log(form_idx);

    // 양식 태그 불러오기
    $.ajax({
        type: 'post',
        url: 'manage_form_detail.ajax',
        data: {
            'form_idx': form_idx
        },
        dataType: 'json',
        success: function(data) {
            // 목록 출력
            printInfo(data.info);
        },
        error: function(e) {
            //console.log(e);
        }
    });
}

function printInfo(info) {
    //console.log(info);
    let tags= '';

    // 문서 양식명
    document.getElementsByName('subject')[0].value = info.form_subject;

    // 문서
    editor.setHTMLCode(info.form_content);
    document.getElementById('div_editor_copy').innerHTML = editor.getHTMLCode();
    //console.log(editor.getHTMLCode());

    // 결재선
    // 1차 결재선
    tags = '';

    if (info.dept_idx_1 === 0 && info.duty_idx_1 === 0) {
        tags += '없음';
        document.getElementById('approval_1').style.display = 'none';
        document.getElementById('approval_duty_1').innerHTML = '';
    } else if (info.dept_idx_1 === 0 && info.duty_idx_1 !== 0) {
        tags += '작성자 소속/' + info.duty_name_1;
        document.getElementById('approval_1').style.display = '';
        document.getElementById('approval_duty_1').innerHTML = info.duty_name_1;
        setHtmlCode();
    } else {
        tags += info.dept_name_1 + '/' + info.duty_name_1;
        document.getElementById('approval_1').style.display = '';
        document.getElementById('approval_duty_1').innerHTML = info.duty_name_1;
        setHtmlCode();
    }

    document.getElementsByClassName('form_approval_line')[0].innerHTML = tags;

    // 2차 결재선
    tags= '';

    if (info.dept_idx_2 === 0 && info.duty_idx_2 === 0) {
        tags += '없음';
        document.getElementById('approval_2').style.display = 'none';
        document.getElementById('approval_duty_2').innerHTML = '';
    } else if (info.dept_idx_2 === 0 && info.duty_idx_2 !== 0) {
        tags += '작성자 소속/' + info.duty_name_2;
        document.getElementById('approval_2').style.display = '';
        document.getElementById('approval_duty_2').innerHTML = info.duty_name_2;
        setHtmlCode();
    } else {
        tags += info.dept_name_2 + '/' + info.duty_name_2;
        document.getElementById('approval_2').style.display = '';
        document.getElementById('approval_duty_2').innerHTML = info.duty_name_2;
        setHtmlCode();
    }

    document.getElementsByClassName('form_approval_line')[1].innerHTML = tags;

    // 3차 결재선
    tags= '';

    if (info.dept_idx_3 === 0 && info.duty_idx_3 === 0) {
        tags += '없음';
        document.getElementById('approval_3').style.display = 'none';
        document.getElementById('approval_duty_3').innerHTML = '';
    } else if (info.dept_idx_3 === 0 && info.duty_idx_3 !== 0) {
        tags += '작성자 소속/' + info.duty_name_3;
        document.getElementById('approval_3').style.display = '';
        document.getElementById('approval_duty_3').innerHTML = info.duty_name_3;
        setHtmlCode();
    } else {
        tags += info.dept_name_3 + '/' + info.duty_name_3;
        document.getElementById('approval_3').style.display = '';
        document.getElementById('approval_duty_3').innerHTML = info.duty_name_3;
        setHtmlCode();
    }

    document.getElementsByClassName('form_approval_line')[2].innerHTML = tags;
}

// 임시 저장
function saveForm() {
    const subject = document.getElementsByName('subject')[0].value;
    const content = editor.getHTMLCode();
    //console.log('input value: ' + subject + '/' + content);

    if (subject != null && subject != '' && subject != 'null') {
        $.ajax({
            type: 'post',
            url: 'manage_form_update.ajax',
            data: {
                'form_idx': form_idx,
                'form_subject': subject,
                'form_content': content
            },
            dataType: 'json',
            success: function(data) {
                //console.log(data.success);
                saveMsg();
            },
            error: function(e) {
                //console.log(e);
            }
        });
    }
}

//const saveInterval = setInterval(saveForm(),120000);

// 문서 양식을 저장할 시 메시지 출력
function saveMsg() {
    document.getElementsByClassName('msg')[0].innerHTML = '<span class="font_caution">문서 양식을 저장했습니다.</span>';
    setTimeout(function() {
        document.getElementsByClassName('msg')[0].innerHTML = '2분마다 자동으로 저장됩니다.';
    }, 5000);
}

// 에디터를 수정하면 카피 div에 html 코드를 저장
editor.attachEvent("change", function () {
    //console.log(editor.getHTMLCode());
    document.getElementById('div_editor_copy').innerHTML = editor.getHTMLCode();
});

// 카피 div의 코드를 에디터에 덮어 씌우기
function setHtmlCode() {
    document.querySelectorAll('#div_editor_copy td:empty').forEach(function(td) {
        td.innerHTML = '<br />';
    });
    let html = document.getElementById('div_editor_copy').innerHTML;
    editor.setHTMLCode(html);
}

// 제목을 입력하면 문서에 반영
function syncTitleToEditor(elem) {
    document.getElementById('form_title').innerHTML = elem.value;
    setHtmlCode();
}

// 문서를 수정하면 제목에 반영 - 사용 안 함
//console.log(editor.document.getElementById('form_title').innerHTML);
function syncTitleToForm() {
    document.getElementsByName('subject')[0].value = document.getElementById('form_title').innerHTML;
    setHtmlCode();
}

// 이전 화면으로 돌아가기
document.getElementsByClassName('form_return')[0].addEventListener('click', () => {
    location.href = '/manage_form_detail.go?form_idx=' + form_idx;
});



// 모달 공통 스크립트
function tst_modal_call(cls) {
    document.getElementsByClassName(cls)[0].style.display = 'flex';
}

function tst_modal_call_param(cls, param) {
    document.getElementsByClassName(cls)[0].style.display = 'flex';
    document.getElementById('tst_modal_param').value = param;
    //console.log(document.getElementById('tst_modal_param').value);

    callDeptToModalDept(114);
}

function tst_modal_close(cls) {
    document.getElementsByClassName(cls)[0].style.display = 'none';
}

// 부서 정보 불러오기
function callDeptToModalDept(idx) {
    $.ajax({
        type: 'post',
        url: 'manage_form_call_dept.ajax',
        data: {
            'dept_high': idx
        },
        dataType: 'json',
        success: function(data) {
            //console.log(data.info);
            fillSelectDept(data.info);
        },
        error: function(e) {
            //console.log(e);
        }
    });
}

function fillSelectDept(info) {
    //console.log(info);
    let tags = '';

    tags += '<option value="0" selected>부서를 선택하세요</option>';
    tags += '<option value="114">대표</option>';

    for (let item of info) {
        tags += '<option value="' + item.dept_idx + '">' + item.dept_name + '부</option>';
    }
    tags += '<option value="0">결재선 비우기</option>';

    document.getElementsByName('select_dept')[0].innerHTML = tags;
}

// 팀 정보 불러오기
function callDeptToModalTeam(idx) {
    $.ajax({
        type: 'post',
        url: 'manage_form_call_dept.ajax',
        data: {
            'dept_high': idx
        },
        dataType: 'json',
        success: function(data) {
            //console.log(data.info);
            fillSelectTeam(data.info, idx);
        },
        error: function(e) {
            //console.log(e);
        }
    });
}

function fillSelectTeam(info, idx) {
    //console.log(info);
    let tags = '';
    tags += '<option value="0" selected>팀을 선택하세요</option>';

    if (idx !== '114') {
        tags += '<option value="' + idx + '">팀 선택 안 함</option>';

        for (let item of info) {
            tags += '<option value="' + item.dept_idx + '">' + item.dept_name + '팀</option>';
        }
    } else if (idx === '114') {
        tags += '<option value="114">대표</option>';
    } else if (idx === '0') {
        tags += '<option value="0">결재선 비우기</option>';
    }

    document.getElementsByName('select_team')[0].innerHTML = tags;
}

// 정보 저장하기
function saveValues() {
    //console.log(document.getElementsByName('select_dept')[0].value);
    //console.log(document.getElementsByName('select_team')[0].value);
    const finalDeptIdx = document.getElementsByName('select_dept')[0].value;
    const finalTeamIdx = document.getElementsByName('select_team')[0].value;
    let msgBox = document.getElementsByClassName('tst_modal_select_msg')[0]

    if (finalDeptIdx === '0' && finalTeamIdx === '0') {
        document.getElementsByName('duty_idx_modal')[0].value = '0';
        msgBox.innerHTML = '결재선을 비우시겠습니까? 이후 결재선까지 같이 비워집니다.';
    } else if (finalTeamIdx === '114') {
        document.getElementsByName('duty_idx_modal')[0].value = '2';
        msgBox.innerHTML = '대표이사를 결재선으로 설정하시겠습니까?';
    } else {
        $.ajax({
            type: 'post',
            url: 'manage_form_call_name.ajax',
            data: {
                'dept_idx': finalTeamIdx
            },
            dataType: 'json',
            success: function(data) {
                if (finalDeptIdx === finalTeamIdx) {
                    document.getElementsByName('duty_idx_modal')[0].value = '3';
                    msgBox.innerHTML = data.name + '부의 부서장을 결재선으로 설정하시겠습니까?';
                } else if (finalDeptIdx !== finalTeamIdx) {
                    document.getElementsByName('duty_idx_modal')[0].value = '4';
                    msgBox.innerHTML = data.name + '팀의 팀장을 결재선으로 설정하시겠습니까?';
                }
            },
            error: function(e) {
                //console.log(e);
            }
        });
    }
}