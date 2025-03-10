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
    <link rel="stylesheet" type="text/css" href="resources/css/manage_rent.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    
    
    
    <link rel="stylesheet" href="resources/richtexteditor/rte_theme_default.css" />
    <script type="text/javascript" src="resources/richtexteditor/rte.js"></script>
    <script type="text/javascript" src="resources/richtexteditor/plugins/all_plugins.js"></script>
    <script type='text/javascript' src="resources/richtexteditor/lang/rte-lang-ko.js"></script>
    <link rel="stylesheet" href="resources/css/module_rte.css" />
    <script type="text/javascript" src="resources/js/module_rte.js"></script>
    
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
                    <li class="tst_title_item tst_title_item_active" onclick="location.href='/manage_rent_list.go'">
                        <h1>공용 물품 관리</h1>
                    </li>
                    <li class="tst_title_item" onclick="location.href='/manage_dispose_list.go'">
                        <h1>폐기 물품 확인</h1>
                    </li>
                </ul>
                <!-- //제목 -->
                
                <form action="productAdd.do" method="post" enctype="multipart/form-data">
                    <div class="tst_flex">

                        <!-- 물품 정보 -->
                        <div class="tst_col9">
                            <table class="tst_table table_align_left table_no_padding table_no_underline">
                                <colgroup>
                                    <col style="width: 120px;" />
                                    <col style="width: auto;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">공용 물품 정보</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="td_align_top">물품명<span class="font_caution"> *</span></th>
                                    <td>
                                        <input type="text" name="subject" maxlength="100" placeholder="물품명을 입력하세요" value=""/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">물품 정보<span class="font_caution"> *</span></th>
                                    <td>
                                        <input type="text" name="information" maxlength="1000" placeholder="물품 정보를 입력하세요" value=""/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">보관 장소<span class="font_caution"> *</span></th>
                                    <td>
                                        <input type="text" name="place" maxlength="1000" placeholder="물품 보관장소를 입력하세요" value=""/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">물품 설명<span class="font_caution"> *</span></th>
                                    <td>
                                        <!-- <textarea name="content" rows="27" placeholder="물품 설명을 입력하세요"></textarea> -->
                                    	<!-- rte -->
										<div id="div_editor"></div>
										<!-- //rte -->
                                    </td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th class="td_align_top"></th>
                                    <td class="td_align_left">
                                        <ul class="list_no_desc list_inline">
                                            <li>
                                                <button type="button" onclick="tst_modal_call('tst_modal_write')" class="btn_primary">물품 등록하기</button>
                                            </li>
                                            <li>
                                                <button type="button" onclick="location.href='/manage_rent_list.go'" class="btn_secondary">목록으로 돌아가기</button>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- //물품 정보 -->

                        <div class="tst_col3">

                            <!-- 대여 정보 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: 90px;" />
                                    <col style="width: auto;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">물품 카테고리</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th>카테고리<span class="font_caution"> *</span></th>
                                    <td>
                                        <select name="category" onchange="change_category_life(this)">
                                            <option value="0">카테고리</option>
                                            <c:forEach items="${categoryList}" var="category">
                                            	<option value="${category.prod_cate_idx}" data-prod-life="${category.prod_life}">
                                            		${category.prod_cate_name}</option>
                                            </c:forEach>                          
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>내용연수</th>
                                    <td id="useful_life" class="useful_life">${category.prod_life}년</td>
                                </tr>
                                <tr>
                                    <th>사용연한</th>
                                    <td id="due_date" class="due_date">
                                    	<input type="hidden" name="prod_date" id="due_date_input" value="">
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <!-- //대여 정보 -->

                            <hr class="separator" />

                            <!-- 첨부 파일 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: auto;" />
                                    <col style="width: 60px;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">설명서</th>
                                </tr>
                                </thead>
                                <tbody id="file_list">

                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="2">
                                    	<input type="file" name="attached_file" id="attached_file" multiple onchange="file_upload(event)" />
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                            <!-- //첨부 파일 -->

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<c:import url="manage_rent_write_modal.jsp" />

<script src="resources/js/common.js"></script>
<script src="resources/js/manage_rent_write.js" defer></script>
</html>