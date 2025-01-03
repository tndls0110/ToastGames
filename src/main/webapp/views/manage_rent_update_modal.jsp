<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 작성하기 -->
<div class="tst_modal tst_modal_alert tst_modal_update">
    <div class="tst_modal_container">
        <div class="tst_modal_header">
            <h1 class="tst_modal_title">공용 물품 정보 수정하기</h1>
            <i class="bi bi-dash-circle-dotted" onclick="cancelUpdate()"></i>
        </div>
        <div class="tst_modal_body">
            <ul class="tst_list list_no_desc list_block">
                <li>
                    <h3 id="prod_name_in_modal">{물품명}</h3>
                </li>
                <li>
                    <p>위 물품의 정보를 수정하시겠습니까?</p>
                </li>
                <li>
                    <hr class="separator" />
                </li>
            </ul>
        </div>
        <div class="tst_modal_footer">
            <div class="tst_flex">
                <div class="tst_col6">
                    <!-- 등록 경로를 입력하세요 --><button onclick="confirmUpdate()" class="btn_primary btn_full" id="product_update_button">정보 수정하기</button>
                </div>
                <div class="tst_col6">
                    <button onclick="cancelUpdate()" class="btn_secondary btn_full">이전 화면으로 돌아가기</button>
                </div>
            </div>
        </div>
    </div>
    <div class="tst_modal_backdrop" onclick="cancelUpdate()"></div>
</div>
<!-- //작성하기 -->

<script src="resources/js/manage_rent_update_modal.js"></script>