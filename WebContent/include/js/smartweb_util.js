/* ---------------------------------------------------------------------- *\
  Description : 입력항목 체크
  Author	  : 최선영 2014.07.10
  Example     : if (!chkSubmit($('#itemNm'),"출력내용"))	return;
\* ---------------------------------------------------------------------- */
function chkSubmit(v_item,v_name)
{
	if( v_item.val().replace(/\s/g,"")=="") {
		alert(v_name+" 확인해주세요.");
		v_item.val("");
		v_item.focus();
		return false;
	} else {
		return true;
	}
}

/* ---------------------------------------------------------------------- *\
Description : enter Key 제어
Author	  : 최선영 2014.07.10
\* ---------------------------------------------------------------------- */

function keyDown(evt){
	var evCode = (window.netscape) ? evt.which : event.keyCode;
	if(evCode == 13){	//enter
		return true;
	}
	return false;
}