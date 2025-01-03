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
    <link rel="stylesheet" type="text/css" href="resources/css/approval.css" />
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
                    <li class="tst_title_item tst_title_item_active" onclick="location.href='/manage_form_list'">
                        <h1>문서 양식 관리</h1>
                    </li>
                </ul>
                <!-- //제목 -->

                <!-- 문서 양식 상세보기 -->
                <div class="tst_flex">

                    <!-- 문서 양식 상세보기 -->
                    <div class="tst_col9">
                        <table class="tst_table table_align_left table_no_padding">
                            <colgroup>
                                <col style="width: 120px" />
                                <col style="width: auto" />
                            </colgroup>
                            <thead>
                            <tr>
                                <th colspan="2">
                                    결재할 문서
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th>문서 양식명</th>
                                <th>{문서 양식명}</th>
                            </tr>
                            <tr>
                                <th>작성 정보</th>
                                <td><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span><!-- 작성일시 --></td>
                            </tr>
                            <tr>
                                <th>최종 수정 정보</th>
                                <td><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span><!-- 최종수정일시 --></td>
                            </tr>
                            <tr>
                                <th>문서 양식</th>
                                <td>{문서 양식}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //문서 양식 상세보기 -->

                    <!-- 문서 양식 정보 확인 -->
                    <div class="tst_col3">

                        <!-- 작성 정보 확인 -->
                        <table class="tst_table table_align_left table_no_padding">
                            <colgroup>
                                <col style="width: 100px" />
                                <col style="width: auto" />
                            </colgroup>
                            <thead>
                            <tr>
                                <th colspan="2">
                                    문서 양식 정보
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th>문서 제목</th>
                                <th>{문서 제목}</th>
                            </tr>
                            <tr>
                                <th>문서 유형</th>
                                <td>{문서 유형} 예)기안서</td>
                            </tr>
                            <tr>
                                <th>작성자</th>
                                <td><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span></td>
                            </tr>
                            </tbody>
                        </table>
                        <!-- //문서 양식 정보 확인 -->

                        <hr class="separator" />

                        <!-- 결재선 설정 -->
                        <table class="tst_table table_align_left">
                            <colgroup>
                                <col style="width: 40px" />
                                <col style="width: auto" />
                            </colgroup>
                            <thead>
                            <tr>
                                <th colspan="2">
                                    결재선 설정
                                </th>
                            </tr>
                            </thead>
                            <tbody>

                            <!-- 1차 결재자 -->
                            <tr>
                                <td class="td_align_top td_no_padding">
                                    <img src="http://t1.daumcdn.net/brunch/service/user/hgs3/image/9JOYw3gnSsO-4srSbvW4LaGayQg.png" alt="{직원명}의 프로필 사진" class="approval_profile_image" />
                                </td>
                                <td class="approval_line_info">
                                    <h4>1차 결재자</h4>
                                    <p><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span></p>
                                </td>
                            </tr>
                            <!-- //1차 결재자 -->

                            <!-- 2차 결재자 -->
                            <tr>
                                <td class="td_align_top td_no_padding">
                                    <img src="http://t1.daumcdn.net/brunch/service/user/hgs3/image/9JOYw3gnSsO-4srSbvW4LaGayQg.png" alt="{직원명}의 프로필 사진" class="approval_profile_image" />
                                </td>
                                <td class="approval_line_info">
                                    <h4>2차 결재자</h4>
                                    <p><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span></p>
                                </td>
                            </tr>
                            <!-- //2차 결재자 -->

                            <!-- 3차 결재자 -->
                            <tr>
                                <td class="td_align_top td_no_padding">
                                    <img src="http://t1.daumcdn.net/brunch/service/user/hgs3/image/9JOYw3gnSsO-4srSbvW4LaGayQg.png" alt="{직원명}의 프로필 사진" class="approval_profile_image" />
                                </td>
                                <td class="approval_line_info">
                                    <h4>최종 결재자</h4>
                                    <p><span onclick="tst_view_profile('{직원 번호}')" class="tst_pointer">{직원명} ({부서}/{직급})</span></p>
                                </td>
                            </tr>
                            <!-- //3차 결재자 -->

                            </tbody>
                        </table>
                        <!-- //결재선 설정 -->

                        <!-- 버튼 -->
                        <ul class="list_no_desc list_block">
                            <li>
                                <button class="btn_primary btn_full" onclick="location.href='/manage_form_update'">양식 수정하기</button>
                            </li>
                            <li>
                                <button class="btn_secondary btn_full" onclick="location.href='/manage_form_write'">양식을 복사하여 새 양식 작성하기</button>
                            </li>
                        </ul>
                        <!-- //버튼 -->

                    </div>
                    <!-- //문서 양식 정보 확인 -->

                </div>
                <!-- //문서 양식 상세보기 -->

            </div>
        </div>
    </div>
</div>
</body>
<script src="resources/js/common.js"></script>
</html>