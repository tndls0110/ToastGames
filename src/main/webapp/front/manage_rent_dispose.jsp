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
                    <li class="tst_title_item tst_title_item_active" onclick="location.href='/'">
                        <h1>공용 물품 관리</h1>
                    </li>
                    <li class="tst_title_item" onclick="location.href='/'">
                        <h1>폐기 물품 확인</h1>
                    </li>
                </ul>
                <!-- //제목 -->
                <form>
                    <div class="tst_flex">

                        <!-- 폐기 정보 -->
                        <div class="tst_col9">
                            <table class="tst_table table_align_left table_no_padding table_no_underline">
                                <colgroup>
                                    <col style="width: 120px;" />
                                    <col style="width: auto;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">폐기 정보</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th class="td_align_top">폐기 사유</th>
                                    <td>
                                        <textarea name="disp_reason" rows="5" maxlength="1000" placeholder="폐기 사유를 입력하세요"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top">사진 첨부</th>
                                    <td>
                                        <input type="file" name="file" placeholder="첨부할 사진을 등록하세요. 장당 최대 5MB, 총 다섯 장까지 등록 가능합니다." />
                                        <div class="image_preview">
                                            <ul class="tst_list list_inline list_no_desc">
                                                <li><img src="https://images3.theispot.com/1024x1024/a4140ir1003.jpg?v=210305104100" /></li>
                                                <li><img src="https://images3.theispot.com/1024x1024/a4140a1012.jpg?v=210305105300" /></li>
                                                <li><img src="https://images3.theispot.com/1024x1024/a4140ir1071.jpg?v=210306093500" /></li>
                                                <li><img src="https://images2.theispot.com/1024x1024/a4140ir1124.jpg?v=211029051300" /></li>
                                                <li><img src="https://images3.theispot.com/1024x1024/a4140ir1062.jpg?v=210305062100" /></li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="td_align_top"><span class="font_subtle">주의사항</span></th>
                                    <td>
                                        <p class="font_subtle">1. 사용 연한이 지나지 않은 물품을 폐기할 때는 반드시 제품 상태 사진을 첨부해야 합니다.</p>
                                        <p class="font_subtle">2. 사용 연한이 지나지 않은 물품은 직원에게 인수할 수 없습니다.</p>
                                        <p class="font_subtle">3. 사용 연한이 지난 물품을 직원에게 인수하기 위해서는 반드시 직원의 사원증을 촬영한 사진을 첨부해야 합니다.</p>
                                    </td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th class="td_align_top"></th>
                                    <td class="td_align_left">
                                        <ul class="list_no_desc list_inline">
                                            <li>
                                                <button type="button" onclick="tst_modal_call('tst_modal_dispose')" class="btn_primary">물품 등록하기</button>
                                            </li>
                                            <li>
                                                <button type="button" onclick="location.href='/'" class="btn_secondary">목록으로 돌아가기</button>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- //폐기 정보 -->

                        <div class="tst_col3">

                            <!-- 물품 정보 -->
                            <table class="tst_table table_align_left table_no_padding">
                                <colgroup>
                                    <col style="width: 90px;" />
                                    <col style="width: auto;" />
                                </colgroup>
                                <thead>
                                <tr>
                                    <th colspan="2">물품 정보</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th>물품명</th>
                                    <td id="prod_name" class="prod_name">{물품명}</td>
                                </tr>
                                <tr>
                                    <th>물품 정보</th>
                                    <td id="prod_cate_idx" class="prod_cate_idx">{물품 정보}</td>
                                </tr>
                                <tr>
                                    <th>카테고리</th>
                                    <td id="prod_cate_name" class="prod_cate_name">{물품 카테고리}</td>
                                </tr>
                                <tr>
                                    <th>내용연수</th>
                                    <td id="prod_life" class="prod_life">{내용연수|0년}</td>
                                </tr>
                                <tr>
                                    <th>등록일</th>
                                    <td id="prod_purch_date" class="prod_purch_date">{등록일|yyyy-MM-dd}</td>
                                </tr>
                                <tr>
                                    <th>사용연한</th>
                                    <td id="prod_dispo_date" class="prod_dispo_date">{사용연한|yyyy-MM-dd}</td>
                                </tr>
                                </tbody>
                            </table>
                            <!-- //믈픔 정보 -->

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<c:import url="manage_rent_dispose_modal.jsp" />

<script src="resources/js/common.js"></script>
<script src="resources/js/manage_rent_disuse.js"></script>
</html>