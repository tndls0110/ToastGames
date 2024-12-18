<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TOAST Games Groupware</title>
    <link rel="stylesheet" type="text/css" href="resources/css/common.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/table.css" />
    <link rel="stylesheet" type="text/css" href="resources/css/pagenation.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="resources/js/jquery.twbsPagination.js"></script>
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
                        <h1>내게 온 업무 요청</h1>
                    </li>
                    <li class="tst_title_item" onclick="location.href='/'">
                        <h1>내가 보낸 업무 요청</h1>
                    </li>
                    <li class="tst_title_item" onclick="location.href='/'">
                        <h1>작성중인 문서</h1>
                    </li>
                </ul>
                <!-- //제목 -->

                <!-- 게시물 분류 -->
                <ul class="tst_tablist list_no_desc list_inline">
                    <li class="tst_tablist_item tst_tablist_item_active" onclick="location.href='/'">
                        <h3>전체 보기</h3>
                    </li>
                    <li class="tst_tablist_item" onclick="location.href='/'">
                        <h3>읽지 않음</h3>
                    </li>
                    <li class="tst_tablist_item" onclick="location.href='/'">
                        <h3>결재중</h3>
                    </li>
                    <li class="tst_tablist_item" onclick="location.href='/'">
                        <h3>결재 승인</h3>
                    </li>
                    <li class="tst_tablist_item" onclick="location.href='/'">
                        <h3>결재 반려</h3>
                    </li>
                    <li class="tst_tablist_item">
                        <form>
                            <div class="tst_search_container">
                                <h3>검색</h3>
                                <input type="text" name="search" maxlength="50" class="input_min input_underline" />
                            </div>
                        </form>
                    </li>
                </ul>
                <!-- //게시물 분류 -->

                <!-- 결재할 문서 목록 -->
                <table class="tst_table">
                    <colgroup>
                        <col style="width: 180px" />
                        <col style="width: 180px" />
                        <col style="width: auto" />
                        <col style="width: 100px" />
                        <col style="width: 150px" />
                        <col style="width: 150px" />
                        <col style="width: 150px" />
                    </colgroup>
                    <thead>
                    <tr>
                        <th>발신인</th>
                        <th>유형</th>
                        <th>제목</th>
                        <th>결재 현황</th>
                        <th>수신 일시</th>
                        <th>처리 일시</th>
                        <th>마감 일시</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- //결재할 문서 목록 -->

                    <!-- 결재할 전자 문서가 없을 경우 -->
                    <tr>
                        <td colspan="7" class="td_no_data">
                            <p>
                                <i class="bi bi-file-earmark-break"></i>
                            </p>
                            <h3>결재할 문서가 없습니다.</h3>
                        </td>
                    </tr>
                    <!-- //결재할 전자 문서가 없을 경우 -->

                    <!-- 기본 -->
                    <tr>
                        <td class="td_align_left">
                            {직원명 (부서명/직급명)}
                        </td>
                        <td>
                            {문서 유형}
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_primary margin_right">{처리 현황}</span>
                            {제목}
                        </td>
                        <td>
                            <span class="tst_badge_min btn_primary">{결재 현황}</span>
                        </td>
                        <td>
                            {수신 일시}
                        </td>
                        <td>
                            {처리 일시}
                        </td>
                        <td>
                            {마감 일시}
                        </td>
                    </tr>
                    <!-- //기본 -->

                    <!-- 예시) 문서를 읽었으나 아직 결재를 마치지 않았을 경우 -->
                    <tr>
                        <td class="td_align_left">
                            김이름 (영업/대리)
                        </td>
                        <td>
                            출장 결과 보고서
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_secondary margin_right">결재중</span>
                            제목이 여기에 출력됩니다.
                        </td>
                        <td>
                            <span class="tst_badge_min btn_secondary">진행중</span>
                        </td>
                        <td>
                            2024-12-18 09:30
                        </td>
                        <td>
                            2024-12-18 10:00
                        </td>
                        <td>
                            2024-12-20 18:00
                        </td>
                    </tr>
                    <!-- //예시) 문서를 읽었으나 아직 결재를 마치지 않았을 경우 -->

                    <!-- 예시) 문서 결재 이후 윗선으로 상신한 문서가 아직 결재되지 않았을 경우 -->
                    <tr>
                        <td class="td_align_left">
                            김이름 (영업/대리)
                        </td>
                        <td>
                            출장 결과 보고서
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_subtle margin_right">승인</span>
                            제목이 여기에 출력됩니다.
                        </td>
                        <td>
                            <span class="tst_badge_min btn_secondary">진행중</span>
                        </td>
                        <td>
                            2024-12-18 09:30
                        </td>
                        <td>
                            2024-12-18 10:00
                        </td>
                        <td>
                            2024-12-20 18:00
                        </td>
                    </tr>
                    <!-- //예시) 문서 결재 이후 윗선으로 상신한 문서가 아직 결재되지 않았을 경우 -->

                    <!-- 예시) 상급자가 결재를 최종 승인하여 시스템이 자동으로 알림을 발송했을 경우 -->
                    <tr>
                        <td class="td_align_left">
                            김이름 (경영지원/대리)
                        </td>
                        <td>
                            교육 참가 신청서
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_subtle margin_right">승인</span>
                            <span class="font_subtle">(시스템 자동 발송) 결재가 최종 승인되었습니다.</span>
                        </td>
                        <td>
                            <span class="tst_badge_min btn_subtle">최종 승인</span>
                        </td>
                        <td>
                            2024-12-18 09:30
                        </td>
                        <td>
                            2024-12-18 10:00
                        </td>
                        <td>
                            2024-12-20 18:00
                        </td>
                    </tr>
                    <!-- //예시) 상급자가 결재를 최종 승인하여 시스템이 자동으로 알림을 발송했을 경우 -->

                    <!-- 예시) 상급자가 결재를 최종 반려하여 시스템이 자동으로 알림을 발송했을 경우 -->
                    <tr>
                        <td class="td_align_left">
                            김이름 (경영지원/대리)
                        </td>
                        <td>
                            교육 참가 신청서
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_subtle margin_right">승인</span>
                            <span class="font_subtle">(시스템 자동 발송) 결재가 반려되었습니다.</span>
                        </td>
                        <td>
                            <span class="tst_badge_min btn_subtle">반려</span>
                        </td>
                        <td>
                            2024-12-18 09:30
                        </td>
                        <td>
                            2024-12-18 10:00
                        </td>
                        <td>
                            2024-12-20 18:00
                        </td>
                    </tr>
                    <!-- //예시) 상급자가 결재를 최종 반려하여 시스템이 자동으로 알림을 발송했을 경우 -->

                    <!-- 예시) 문서를 반려했을 경우 -->
                    <tr>
                        <td class="td_align_left">
                            김이름 (영업/대리)
                        </td>
                        <td>
                            출장 결과 보고서
                        </td>
                        <td class="td_align_left">
                            <span class="tst_badge_min btn_subtle margin_right">반려</span>
                            제목이 여기에 출력됩니다.
                        </td>
                        <td>
                            <span class="tst_badge_min btn_subtle">반려</span>
                        </td>
                        <td>
                            2024-12-18 09:30
                        </td>
                        <td>
                            2024-12-18 10:00
                        </td>
                        <td>
                            2024-12-20 18:00
                        </td>
                    </tr>
                    </tbody>
                    <!-- //예시) 문서를 반려했을 경우 -->

                    <!-- pagination -->
                    <tfoot>
                    <tr>
                        <td colspan="7">
                            <ul id="pagination" class="pagination-sm pagination">
                                <li class="page-item first disabled">
                                    <a href="#" class="page-link"><i class="bi bi-chevron-double-left"></i></a>
                                </li>
                                <li class="page-item prev disabled">
                                    <a href="#" class="page-link"><i class="bi bi-chevron-left"></i></a>
                                </li>
                                <li class="page-item active">
                                    <a href="#" class="page-link">1</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">2</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">3</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">4</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">5</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">6</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">7</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">8</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">9</a>
                                </li>
                                <li class="page-item">
                                    <a href="#" class="page-link">10</a>
                                </li>
                                <li class="page-item next">
                                    <a href="#" class="page-link"><i class="bi bi-chevron-right"></i></a>
                                </li>
                                <li class="page-item last">
                                    <a href="#" class="page-link"><i class="bi bi-chevron-double-right"></i></a>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    </tfoot>
                    <!-- //pagination -->

                </table>
                <!-- //결재할 문서 목록 -->

            </div>
        </div>
    </div>
</div>
</body>
<script src="resources/js/common.js"></script>
</html>