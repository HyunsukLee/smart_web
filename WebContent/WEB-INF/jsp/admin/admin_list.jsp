<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/header.jspf"%>

	<script type="text/javascript">
	$(document).ready(function() {
	// Datepicker 설정 
		$('#START_DATE,#END_DATE').datepicker({
	 		dateFormat: 'yy-mm-dd',
	 		buttonImage: '/images/common/btn_calendar.gif',
	 		onSelect: function(dateText, inst) {
	 			if( $('#START_DATE').val() != "" && $('#END_DATE').val() != "" ) {
	 				if( $('#START_DATE').val() > $('#END_DATE').val() ) {
	 					alert("시작날짜와 종료날짜의 선후 관계를 확인 하시기 바랍니다.");
	 	 				$("#"+inst.id).val(inst.lastVal);
	 	 			}
	 			}
	 		}
	 	});
	
		$("#pageSize").change(function() {
			goPage(1);
		});
	});
	
	function goPage(page){
		$("#page").val(page);
		$("#admin_searchform").attr("method","GET");
		$("#admin_searchform").attr("action","");
		$("#admin_searchform").submit();
	}
	
	function setOrder(order_by){
		$("#ORDER_BY").val(order_by);
		if($("#ORDER_SC").val()=='DESC'){
			$("#ORDER_SC").val('ASC');
		}else{
			$("#ORDER_SC").val('DESC');
		}
		goPage(1);
	}
	
	//checkBoxChange : 체크박스 전체 변경
	function checkBoxChange() {
		var chkStatus = $("#chkAllSelect").is(":checked");
		if(chkStatus){
			$("#tbList").find("tr td :checkbox").attr("checked", true);
		}else{
			$("#tbList").find("tr td :checkbox").attr("checked", false);
		}
	}
	
	function openPopup(){
		var checkCount=0;
		$("#tbList").find("tr td :checkbox").each(function() {
			if($(this).is(":checked")){
				checkCount++;
			}
		});
		
		if(checkCount==0){
			alert("상태변경 할 관리자를 선택해 주세요.");
			return;
		}
		
		var height = 0;
		var width = 0;
		
		if(window.innerHeight != undefined){
			height = window.innerHeight;
			width = window.innerWidth;
		}else{
			var b = document.body, d = document.documentElement;
			height = Math.max(d.clientHeight, b.clientHeight);
			width = Math.max(d.clientWidth, b.clientWidth);
		}
		var height1 = $("#popUp").height();
		var width1 = $("#popUp").width();
		var top = height/2-height1/2;
		var left = width/2-width1/2;
		
		$("#popBack").show();
		$("#popUp").css({"top":top+"px", "left":left+"px"}).show();
	}

	function closePopup(){
		$("#popBack").hide();
		$("#popUp").hide();
	}
	
	//stsChange : 상태 일괄변경여부 확인
	////use_yn : 변경 할 상태 A-사용, D-미사용
	function stsChange(use_yn){
		var status = '';
		switch(use_yn){
		case "A":
			status = "사용";
			break;
		case "D":
			status = "미사용";
			break;
		case "S":
			status = "사용중지";
			break;
		}
		var ment = '선택하신 관리자를 ['+status+'] 상태로 변경하시겠습니까?';
		if(confirm(ment)){
			//상태 변경
			updateAdminSts('/admin/update_adminSts.do', use_yn);
		}
	}
	
	//updateAdminSts : 상태 일괄변경
	////use_yn : 변경 할 상태 A-사용, D-미사용, S-사용중지
	function updateAdminSts(url, use_yn){
		var checkList = [];
		$("#tbList").find("tr td input[id^='check']:checked").each(function(){
			checkList.push($(this).siblings("input:hidden").val());
		});
		
		$.ajax({
			url: url,
			type: "POST",
			data: {"checkList":checkList.join(","), "UPD_ADM_STE":use_yn},
			success: function(response){
				if(Number(response)>0){
					alert('변경 되었습니다.');
					closePopup();
					$("#tbList").find("tr td :checkbox").attr("checked", false);
					location.reload();
				}else{
					alert('시스템 오류 입니다.');
				}
			}
		});
	}
	
	function searchFormReset(){
		$("#START_DATE").val($("#DEF_START_DATE").val());
		$("#END_DATE").val($("#DEF_END_DATE").val());
		$("#ADM_STS").find(":first-child").attr("selected", true);
		$("#ADM_SEARCH_BY").find(":first-child").attr("selected", true);
		$("#SEARCH").val("");
	}
	
	function goBackFirstStep(){
		//searchFormReset();
		//goPage(1);
		location.href="/admin/admin_list.do";
	}
	
	function openInsertForm(){
		$("#admin_searchform").attr("method","POST");
		$("#admin_searchform").attr("action","/admin/admin_insert.do");
		$("#admin_searchform").submit();
	}
	
	function goAdmin(adminSeq){
		$("#ADMIN_SEQ").val(adminSeq);
		$("#admin_searchform").attr("method","POST");
		$("#admin_searchform").attr("action","/admin/admin_detail.do");
		$("#admin_searchform").submit();
	}
	
	function keyDown(evt){
		var evCode = (window.netscape) ? evt.which : event.keyCode;
		if(evCode == 13){	//엔터
			return true;
		}
		return false;
	}
	
	function excel(){
		$("#admin_searchform").attr('action','/admin/admin_excel.do');
		$("#admin_searchform").submit();
	}
	</script>
	
	<!--Contents Title-->
	<div id="title">
		<div class="title_txt">관리자계정 목록</div>
		<div class="location"><span class="home">HOME</span> &gt; 시스템 관리 &gt; <span class="page">관리자 관리</span></div>
	</div>
	<!--Contents Title end-->
	
	<!-- Content Start -->
	<div id="content">
		<form id="admin_searchform">
			
			<!--Search Start-->
			<div id="search">
				<div class="mb10">
					<input type="hidden" id="page" name="page" value="${prm.page}"/>
					<input type="hidden" id="ORDER_BY" name="ORDER_BY" value="${prm.ORDER_BY}"/>
					<input type="hidden" id="ORDER_SC" name="ORDER_SC" value="${prm.ORDER_SC}"/>
					<input type="hidden" id="DEF_START_DATE" name="DEF_START_DATE" value="${prm.DEF_START_DATE}"/>
					<input type="hidden" id="DEF_END_DATE" name="DEF_END_DATE" value="${prm.DEF_END_DATE}"/>
					<input type="hidden" id="displayCount" name="displayCount" value="${displayCount}"/>
					<input type="hidden" id="ADM_SEQ" name="ADMIN_SEQ" value=""/>
					<input type="hidden" id="chiefYN" name="chiefYN" value="${chiefYN}">
					
					<label class="ml26 mr26 ac">등록일자</label>
					<input type="text" class="input120 vm ml5 mr5" id="START_DATE" name="START_DATE" value="${prm.START_DATE }" readonly="readonly"/>
					<label> ~ </label>
					<input type="text" class="input120 vm mr5" id="END_DATE" name="END_DATE" value="${prm.END_DATE }" readonly="readonly"/>
					<select class="input100 vm mr5 ml13" id="ADM_STS" name="ADM_STS">
						<option value="ALL" <c:if test="${prm.ADM_STS=='ALL'}">selected</c:if>>- 계정 상태 -</option>
						<option value="A" <c:if test="${prm.ADM_STS=='A'}">selected</c:if>>사용</option>
						<option value="D" <c:if test="${prm.ADM_STS=='D'}">selected</c:if>>미사용</option>
						<option value="S" <c:if test="${prm.ADM_STS=='S'}">selected</c:if>>사용중지</option>
					</select>
				</div>
				<div>
					<select class="input100 vm mr5" id="ADM_SEARCH_BY" name="ADM_SEARCH_BY">
						<option value="ALL">- 검색조건 -</option>
						<option value="ALL" <c:if test="${prm.SEARCH!=null && prm.SEARCH!='' && prm.ADM_SEARCH_BY=='ALL'}">selected</c:if>>전체</option>
						<option value="ADM_NM" <c:if test="${prm.ADM_SEARCH_BY=='ADM_NM'}">selected</c:if>>성명</option>
						<option value="ADM_ID" <c:if test="${prm.ADM_SEARCH_BY=='ADM_ID'}">selected</c:if>>관리자ID</option>
						<option value="TEL_NO" <c:if test="${prm.ADM_SEARCH_BY=='TEL_NO'}">selected</c:if>>전화번호</option>
						<option value="EMAIL" <c:if test="${prm.ADM_SEARCH_BY=='EMAIL'}">selected</c:if>>메일주소</option>
						<option value="CORP_NM" <c:if test="${prm.ADM_SEARCH_BY=='CORPNM'}">selected</c:if>>회사명</option>
						<option value="PART" <c:if test="${prm.ADM_SEARCH_BY=='PART'}">selected</c:if>>부서</option>
						<option value="OPOS" <c:if test="${prm.ADM_SEARCH_BY=='OPOS'}">selected</c:if>>직위</option>
						<option value="MOP_NO" <c:if test="${prm.ADM_SEARCH_BY=='MOP_NO'}">selected</c:if>>핸드폰번호</option>
					</select>
					<input type="text" class="input300 vm mr10" id="SEARCH" name="SEARCH" maxlength="50" onkeydown="if(keyDown(event)) goPage(1);" value="${prm.SEARCH}">
					<a href="javascript:goPage(1);" class="btnstyle_orange mr5"><span><img src="../images/common/bu_ser.gif" alt="검색" /> 검색</span></a>
					<a href="javascript:searchFormReset();"  id="cancleSearch" class="btnstyle1 mr5"> <span><img src="/images/common/bu_cancel.gif" alt="초기화" /> 초기화</span></a>
					<a href="javascript:excel();" class="btnstyle1"><span><img src="../images/common/ico_save.gif" alt="엑셀 다운로드" /> 엑셀 다운로드</span></a>
				</div>
			</div>
			<!-- Search End -->
			
			<div class="ar mt10"></div>
			<table summary="검색 결과 및 보여줄 페이지사이즈 선택" class="border_none w100p">
				<tr>
					<td>
						검색결과: ${total}건
					</td>
					<td align="right">
						한 페이지에
						<select class="input100 vm mr5" id="pageSize" name="pageSize">
							<option value="20" <c:if test="${prm.pageSize=='20'}">selected</c:if> >20줄</option>
							<option value="30" <c:if test="${prm.pageSize=='30'}">selected</c:if> >30줄</option>
							<option value="50" <c:if test="${prm.pageSize=='50'}">selected</c:if> >50줄</option>		
							<option value="70" <c:if test="${prm.pageSize=='70'}">selected</c:if> >70줄</option>
							<option value="100" <c:if test="${prm.pageSize=='100'}">selected</c:if> >100줄</option>				
						</select>
					</td>
				</tr>
			</table>
		</form>
		
		<!-- list -->
		<div class="boardlist">
			<table summary="관리자 리스크" id="tbList">
				<caption>리스트</caption>
				<colgroup>
					<col width="5%" />
					<col width="15%" />
					<col width="20%" />
					<col width="15%" />
					<col width="20%" />
					<col width="13%" />
					<col width="7%" />	
				</colgroup>
				<tr>
					<td><input type="checkbox" id="chkAllSelect" onClick="checkBoxChange()"></td>
					<td>
						<a href="javascript:setOrder('ADM_NM');">
							이름
							<c:choose>
								<c:when test="${prm.ORDER_BY=='NAME' and  prm.ORDER_SC=='ASC'}">▲</c:when>
								<c:when test="${prm.ORDER_BY=='NAME' and  prm.ORDER_SC=='DESC'}">▼</c:when>
							</c:choose>
						</a>
					</td>
					<td>
						<a href="javascript:setOrder('ADM_ID');">
							관리자 ID
							<c:choose>
								<c:when test="${prm.ORDER_BY=='ADM_ID' and  prm.ORDER_SC=='ASC'}">▲</c:when>
								<c:when test="${prm.ORDER_BY=='ADM_ID' and  prm.ORDER_SC=='DESC'}">▼</c:when>
							</c:choose>
						</a>
					</td>
					<td>
						전화번호
					</td>
					<td>
						메일주소
					</td>
					<td>
						<a href="javascript:setOrder('CRE_DATE');">
							등록일자
							<c:choose>
								<c:when test="${prm.ORDER_BY=='CREDATE' and  prm.ORDER_SC=='ASC'}">▲</c:when>
								<c:when test="${prm.ORDER_BY=='CREDATE' and  prm.ORDER_SC=='DESC'}">▼</c:when>
							</c:choose>
						</a>
					</td>
					<td>
						<a href="javascript:setOrder('ADM_STS');">
							상태
							<c:choose>
								<c:when test="${prm.ORDER_BY=='ADM_STS' and  prm.ORDER_SC=='ASC'}">▲</c:when>
								<c:when test="${prm.ORDER_BY=='ADM_STS' and  prm.ORDER_SC=='DESC'}">▼</c:when>
							</c:choose>
						</a>
					</td>
				</tr>
				<c:choose>
					<c:when test="${empty list}">
					<%-- c:if test="${fn:length(list)==0}"--%>
						<tr style="height: ${displayCount*29}px;">
							<td colspan="7" valign="top">
								검색조건과 일치하는 검색결과가 없습니다.<br>
								다시 검색해 주세요.<br>
								<a class="btnStyle5" id="btnOk" href="javascript:goBackFirstStep();"><span>확인</span></a>
							</td>
						</tr>
					<%--/c:if --%>
					</c:when>
					<c:otherwise>
						<c:forEach var="admin" items="${list}" varStatus="status">
							<tr>
								<td>
									<input type="hidden" id="checkSeq${status.count }" value="${admin.ADM_SEQ}">
									<input type="checkbox" id="checkBox${status.count }">
								</td>
								<td><a href="javascript:goAdmin('${admin.ADM_SEQ}')"><tag:mask type="name" value="${admin.ADM_NM}"/></a></td>
								<td><tag:mask type="id" value="${admin.ADM_ID }"/></td>
								<td><tag:mask type="tel" value="${admin.TEL_NO }"/></td>
								<td><tag:mask type="email" value="${admin.EMAIL }"/></td>
								<td><fmt:formatDate value="${admin.CREDATE }" pattern="yyyy-MM-dd"/> </td>
								<td><c:choose>
										<c:when test="${admin.ADM_STS =='A'}">사용</c:when>
										<c:when test="${admin.ADM_STS =='S'}">사용중지</c:when>
										<c:when test="${admin.ADM_STS =='D'}">미사용</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<table class="w100p">
			<tr>
				<td class="w20p">
					<c:if test="${chiefYN=='Y' }"><a class="btnstyle1" id="updateSTS" href="javascript:openPopup();"><span><img src="/images/common/bu_check.gif" /> 상태변경</span></a></c:if>
				</td>
				<td><tag:paging page="${param.page}" total="${total}" list_size="${prm.pageSize}"/></td>
				<td class="w20p ar">
					<c:if test="${chiefYN=='Y' }"><a class="btnstyle1" id="updateSTS" href="javascript:openInsertForm();"><span><img src="/images/common/note.gif" /> 관리자 등록</span></a></c:if>
				</td>
			</tr>
		</table>
		<!-- 레이어 팝업 시작 -->
		<div id="popBack">
		</div>
		<div id="popUp">
			<form method="post">
				<div id="popUpWrap">
					<div class="b ac mb5">관리자 상태 변경</div>
					<table class="w100p mb5">
						<tr>
							<td class="ac w50p">
								<a href="javascript:stsChange('A');" class="btnstyle1"><span style="width:58px;">사용</span></a>
							</td>
							<td class="ac w50p">
								<a href="javascript:stsChange('S');" class="btnstyle1"><span style="width:58px;">사용중지</span></a>
							</td>
						</tr>
					</table>
					<div class="ar"><a href="javascript:closePopup();" class="btnstyle1"><span>취소</span></a></div>
				</div>
			</form>
		</div>
	</div>
<!-- Content end -->
<%@ include file="/WEB-INF/jsp/include/footer.jspf"%>