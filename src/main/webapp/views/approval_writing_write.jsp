<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TOAST Games Groupware</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/module_table.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/module_search_min.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/approval.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/approval_send_modal.css" />

    <%--폼 양식 css--%>
    <link rel="stylesheet" href="/resources/css/approval_form.css" />

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<c:import url="layout_topnav.jsp" />
<div class="tst_container">
    <c:import url="layout_leftnav.jsp" />
    <div class="tst_container_right">
        <div class="tst_contents">
            <div class="tst_contents_inner">

                <!-- 제목 -->
                <ul class="tst_title list_no_desc list_inline">
                    <li class="tst_title_item tst_title_item_active" onclick="location.href='/approval_writing_select_form'">
                        <h1>문서 작성하기</h1>
                    </li>
                </ul>
                <!-- //제목 -->

                <form  method="POST" enctype="multipart/form-data">
                    <input type = "hidden" name = "doc_idx" class="hidden_doc_idx"/>
                    <input type = "hidden" name = "doc_write_date" id="hidden_doc_date"/>

                    <div class="tst_flex">

                        <!-- 문서 작성하기 -->
                        <div class="tst_col9">
                            <table class="tst_table table_align_left table_no_underline table_no_padding">
                                <colgroup>
                                    <col style="width: 120px;" />
                                    <col style="width: auto;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">문서 작성</th>
                                </tr>
                                </thead>


                                <tbody>
                                <tr>
                                    <th class="td_align_top">문서 제목</th>
                                    <td>
                                        <input type="text" name="doc_subject" maxlength="100" placeholder="문서 제목을 입력하세요" />
                                    </td>
                                </tr>

                                <tr>
                                    <th class="td_align_top">결재 마감일시</th>
                                    <td>
                                        <input type="datetime-local" name="doc_end_date" id="doc_end_date" maxlength="100"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">보고 내용</th>
                                    <td>
                                        <textarea name="content" rows="5" maxlength="1000" placeholder="보고할 내용을 입력하세요"></textarea>
                                        <input type="hidden" name="doc_content_sub" class ="content"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">문서</th>
                                    <td id="form_content">{문서 소스}</td>
                                    <input type = "hidden" name = "doc_content"/>

                                </tr>
                                </tbody>

                                <tfoot>
                                <tr>
                                    <th class="td_align_top"></th>
                                    <td class="td_align_left">
                                        <ul class="list_no_desc list_inline">
                                            <li>
                                                <button type="button" onclick="approval_request()" class="btn_primary">상신하기</button>
                                            </li>
                                            <li>
                                                <button type="button" onclick="approval_write_save()" class="btn_secondary">임시 저장하기</button>
                                            </li>
                                            <li>
                                                <button type="button" onclick="tst_modal_call('tst_modal_cancel')" class="btn_subtle">작성 취소하기</button>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- //문서 작성하기 -->

                        <div class="tst_col3">

                            <!-- 결재선 편집 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: 90px;" />
                                    <col style="width: auto;" />
                                    <col style="width: 60px;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="3">결재선</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th>1차 결재</th>
                                    <td class="td_align_left" id="approval_line1"></td>
                                    <input type="hidden" name = "empl_line1" id="empl_line1"/>
                                    <input type="hidden" name = "dept_line1" id="dept_line1"/>
                                    <input type="hidden" name = "duty_line1" id="duty_line1"/>
                                    <input type="hidden" name = "position_line1" id="position_line1"/>
                                    <td>
                                        <button type="button" onclick="appr_show_modal(1)" class="btn_primary btn_min">변경하기</button>                                    </td>
                                </tr>
                                <tr>
                                    <th>2차 결재</th>
                                    <td class="td_align_left" id="approval_line2"></td>
                                    <input type="hidden" name = "empl_line2"  id = "empl_line2"/>
                                    <input type="hidden" name = "dept_line2" id = "dept_line2"/>
                                    <input type="hidden" name = "duty_line2" id = "duty_line2"/>
                                    <input type="hidden" name = "position_line2" id="position_line2"/>
                                    <td>
                                        <button type="button" onclick="appr_show_modal(2)" class="btn_primary btn_min">변경하기</button>
                                    </td>
                                </tr>
                                <tr>
                                    <th>최종 결재</th>
                                    <td class="td_align_left" id="approval_line3"></td>
                                    <input type="hidden" name = "empl_line3" id = "empl_line3"/>
                                    <input type="hidden" name = "dept_line3" id = "dept_line3"/>
                                    <input type="hidden" name = "duty_line3" id = "duty_line3"/>
                                    <input type="hidden" name = "position_line3" id="position_line3"/>
                                    <td>
                                        <button type="button" onclick="appr_show_modal(3)" class="btn_primary btn_min">변경하기</button>
                                    </td>
                                </tr>
                                </tbody>
                            </table>

                            <hr class="separator" />
                            <!-- //결재선 편집 -->

                            <!-- 참조 편집 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: 90px;" />
                                    <col style="width: auto;" />
                                    <col style="width: 60px;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="3">참조</th>
                                </tr>
                                </thead>
                                <tbody id="referrer_list">

                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="3">
                                        <button type="button" onclick="appr_show_modal(0)" class="btn_subtle btn_full">참조 추가하기</button>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                            <hr class="separator" />
                            <!-- //참조 편집 -->

                            <!-- 첨부 파일 편집 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: auto;" />
                                    <col style="width: 60px;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">첨부 파일</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="td_align_left">{첨부 파일명 (용량kb)}</td>
                                    <td>
                                        <!-- 삭제 경로/함수를 입력하세요 --><button type="button" onclick="location.href='/'" class="btn_primary btn_min">삭제하기</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_align_left">{첨부 파일명 (용량kb)}</td>
                                    <td>
                                        <!-- 삭제 경로/함수를 입력하세요 --><button type="button" onclick="location.href='/'" class="btn_primary btn_min">삭제하기</button>
                                    </td>
                                </tr>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="2">
                                            <input type="file" name="attached_file" multiple="multiple" />
                                        </td>
                                    </tr>

                                </tfoot>
                            </table>
                            <!-- //첨부 파일 편집 -->

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<c:import url="approval_writing_write_modal.jsp" />
</body>
<script src="resources/js/common.js"></script>
<script src="resources/js/approval_send_modal.js"></script>

<script src="/resources/js/approval_request_form_row.js"></script>
<script src="/resources/js/approval_request_form_adjust.js"></script>
<script src="/resources/js/approval_request_save.js"></script>
<script src="/resources/js/approval_write_line_parent.js"></script>
<script src="/resources/js/approval_write_line_child.js"></script>

<script src="/resources/js/approval_write_delete.js"></script>
<script src="/resources/js/approval_request.js"></script>

<script src="/resources/js/approval_request_write.js"></script>

<script>
    //모델앤뷰로 받은 파라메터
    console.log("${form_idx}");
    console.log("${doc_idx}");


    var doc_idx = "${doc_idx}";
    var form_idx = "${form_idx}";


    //quill editor -> 나중에 import하면 사용하기
   /* const quill = new Quill('#editor', {
        theme: 'snow'
    });*/

</script>

</html>