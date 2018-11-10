<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />  
<meta http-equiv="Expires" content="-1" />  
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>pay</title>
<style>
.box{ width:450px; height:240px; margin:0 auto; position:absolute; top:50%; left:50%; margin-top:-99px; margin-left:-225px; text-align:center;}
</style>
</head>
<body>
        <div class="box"><img src="images/jump_pic01.png" /></div>
        <form id="payBillForm" action="<%=request.getAttribute("req_url")%>" method="post">
                <input type="hidden" name="version" value="<%=request.getAttribute("version")%>"/>
                <input type="hidden" name="oid_partner" value="<%=request.getAttribute("oid_partner")%>"/>
                <input type="hidden" name="user_id" value="<%=request.getAttribute("user_id")%>"/>
                <input type="hidden" name="sign_type" value="<%=request.getAttribute("sign_type")%>"/>
                <input type="hidden" name="sign" value="<%=request.getAttribute("sign")%>"/>
                <input type="hidden" name="busi_partner" value="<%=request.getAttribute("busi_partner")%>"/>
                <input type="hidden" name="no_order" value="<%=request.getAttribute("no_order")%>"/>
                <input type="hidden" name="dt_order" value="<%=request.getAttribute("dt_order")%>"/>
                <input type="hidden" name="name_goods" value="<%=request.getAttribute("name_goods")%>"/>
                <input type="hidden" name="info_order" value="<%=request.getAttribute("info_order")%>"/>
                <input type="hidden" name="money_order" value="<%=request.getAttribute("money_order")%>"/>
                <input type="hidden" name="notify_url" value="<%=request.getAttribute("notify_url")%>"/>
                <input type="hidden" name="url_return" value="<%=request.getAttribute("url_return")%>"/>
                <input type="hidden" name="userreq_ip" value="<%=request.getAttribute("userreq_ip")%>"/>
                <input type="hidden" name="url_order" value="<%=request.getAttribute("url_order")%>"/>
                <input type="hidden" name="valid_order" value="<%=request.getAttribute("valid_order")%>"/>
                 <input type="hidden" name="timestamp" value="<%=request.getAttribute("timestamp")%>"/>
                 <input type="hidden" name="risk_item" value='<%=request.getAttribute("risk_item")%>'/>
                 <input type="hidden" name="no_agree" value="<%=request.getAttribute("no_agree")%>"/>
                 <input type="hidden" name="id_type" value="<%=request.getAttribute("id_type")%>"/>
                  <input type="hidden" name="id_no" value="<%=request.getAttribute("id_no")%>"/>
                  <input type="hidden" name="acct_name" value="<%=request.getAttribute("acct_name")%>"/>
                   <input type="hidden" name="flag_modify" value="<%=request.getAttribute("flag_modify")%>"/>
                   <input type="hidden" name="card_no" value="<%=request.getAttribute("card_no")%>"/>
                  <input type="hidden" name="back_url" value="<%=request.getAttribute("back_url")%>"/>
        </form>
</body>
</html>
 <script language="javascript" type="text/javascript">
 window.onload=function(){
 document.getElementById("payBillForm").submit();
 }
  </script>
