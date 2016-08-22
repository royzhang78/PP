
<%@page import="com.tp.comm.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="head.jsp"></jsp:include>

<body>

	<div class="box-header grd-white corner-top">


		<div class="header-control">
		<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th width="33%"><div align="right">支付渠道 
						 <select id="find_channel_type" name="find_channel_type">
						  <option value="">all</option>
						</select>
				         
					  </div></th>
					  <th width="33%"><div align="right">货币类型 
						  <select id="find_currency_type" name="find_currency_type">
						  <option value="">all</option>
						<jsp:include page="currency.jsp"></jsp:include>
						</select>
				         
					  </div></th>
						<th width="33%"><input type="button" name="add" onClick="pageshow(1);" value="查询"></th>
						<th width="33%"> </th>
						<th width="33%"> </th>
					</tr>
				</thead>
			
		      </table>
		</div>
		<div class="header-control"> <input type="button" name="add" onClick="rt();ShowDiv('MyDiv','fade');" value="渠道价格设定"></div>
		<div class="box-body">
			<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th>渠道id</th>						
						<th>物品价格</th>
						<th>货币种类</th>
						<th>匹配模式</th>
						<th>操作</th>
					</tr>
				</thead>
				<script id="channelPriceTmpl" type="text/x-jquery-tmpl">
	        		<tr class="odd gradeA">
	           			<td >\${channelType(channel_type)}</td>

	            		<td >\${pricepoin}</td>
	            		<td >\${currency}</td>
						<td >\${priceType(type)}</td>
						<td >\${showstatus(status)}</td>
						<td ><input type="button" name="add" onClick="javascript:updateshow('\${id}');" value="修改"></td>
	           		 </tr>
				</script>
				
				
				<tbody id="channelPricelist">
					
					
				

				</tbody>
			</table>

		</div>
		<div class="page" id="page5"></div>
		<div class="page" id="pageDiv"></div>
		
		<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" style="width:600px;display: none;	position: absolute;	top: 5%;left: 25%;border: 1px solid lightblue;background-color:FFFFFF;z-index:1002;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="CloseDiv('MyDiv','fade');">x</button>
			<div class="modal-header" style="background:#F5F5F5;">
			
			<table class="table table-bordered table-striped responsive" border="0">
			<form id="saveForm" name="saveForm" action="">
			  <tr>
				<td>支付价格(可以使用正则)</td>
				<td><input id="id" name="id" type="hidden" /> <input id="pricepoin" name="pricepoin" type="text"></td>
				<td></td>
					<td></td>
			  </tr>
			  <tr>
				 <td>支付渠道</td>
				<td> <select id="channel_type" name="channel_type">
						  
						</select>
						</td>
				<td></td>
					<td></td>
			  </tr>
			   <tr>
				 <td>货币种类</td>
				<td> <select id="currency" name="currency">
					
					<jsp:include page="currency.jsp"></jsp:include>
					</select>
					</td>
					<td></td>
					<td></td>
			  </tr>
			  
			   <tr>
				 <td>状态</td>
				<td> <select id="status" name="status">
				  <option value="2">审核通过</option>
				  <option value="3">下架</option>
				</select>
				</td>
				<td></td>
				<td></td>
			  </tr>
			  
			   <tr>
				 <td>匹配模式</td>
				<td> 
				<select id="type" name="type">
				<option value="0">完全匹配</option>
				<option value="1">正则匹配</option>
				</select>
				</td>
				<td></td>
				<td></td>
			  </tr>
			  <thead id="addKEY">
					
				</thead>
				
			  <tr >
				 <td colspan="2" ><input type="button" name="add" onClick="save();" value="保存">
				 <td colspan="2" ><input type="button" name="add" onClick="addkey();" value="添加自定义属性"></td>
			  </tr>
			  </form> 
			</table>

<script id="addKEYTmpl" type="text/x-jquery-tmpl">
	        		<tr class="odd gradeA">
	           			<td >key</td>

	            		<td ><input id="returnKey" name="returnKey" value="\${key}" type="text"></td>
	            		<td >value</td>
						<td ><input id="returnValue" name="returnValue" value="\${val}" type="text"></td>
					</tr>
				</script>
				
			</div>
			
		</div>
		
		<jsp:include page="hidAllDiv.jsp"></jsp:include>
	</div>
	
	<script src="<%=path %>/js/pay/configChannelPrice.js"></script>
	
</body>
</html>