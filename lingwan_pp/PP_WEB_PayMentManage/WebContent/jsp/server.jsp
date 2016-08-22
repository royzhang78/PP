
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
						<th width="33%"><div align="right">服务器名称 
				          <input type="text" id="find_server_name" name="server_name" >
					  </div></th>
						<th width="33%"><input type="button" name="add" onClick="pageshow(1);" value="查询"></th>
						<th width="33%"> </th>
						<th width="33%"> </th>
					</tr>
				</thead>
			
		      </table>
		</div>
		<div class="header-control"> <input type="button" name="add" onClick="rt();ShowDiv('MyDiv','fade');" value="添加服务器"></div>
		<div class="box-body">
			<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th>服务器名称</th>
						<th>服务器ip</th>
						<th>支付回调url</th>
						<th>状态</th>
						<th>是否沙箱</th>
						<th>创建日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<script id="serverTmpl" type="text/x-jquery-tmpl">
	        		<tr class="odd gradeA">
	           			<td >\${s_name}</td>
	            		<td >\${s_ip}</td>
						<td >\${s_call_payment_url}</td>
						<td >\${showstatus(s_status)}</td>
						<td >{{html Sandboxshow(isSandbox)}}</td>
						<td >\${s_create_date}</td>
						<td ><input type="button" name="add" onClick="javascript:updateshow('\${s_id}');" value="修改">
							<input type="button" name="add" onClick="javascript:updateChannelshow('\${s_id}');" value="渠道绑定">
							<input type="button" name="add" onClick="javascript:updateProductshow('\${s_id}');" value="渠道物品">
						</td>
	           		 </tr>
				</script>
				
				
				<tbody id="serverlist">
					
					
				

				</tbody>
			</table>

		</div>
		<div class="page" id="page5"></div>
		<div class="page" id="pageDiv"></div>
		
		<div id="fade" class="black_overlay"></div>
		
		<div id="channelDiv" style="width:600px;display: none;	position: absolute;	top: 5%;left: 25%;border: 1px solid lightblue;background-color:FFFFFF;z-index:1002;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="CloseDiv('channelDiv','fade');">x</button>
			<div class="modal-header" style="background:#F5F5F5;">
			
			<script id="serverChannelTmpl" type="text/x-jquery-tmpl">
	        		<tr class="odd gradeA">
	           			<td ><input name="channel_id" type="checkbox" value="\${channel_id}" />\${channel_id}</td>
	            		<td >\${channel_name}</td>
						<td >\${channelType(type)}</td>
						<td ><input id="range\${channel_id}" name="range" type="text" values="\${range}" size="5"/></td>
						<td ><textarea  rows="3" id="key\${channel_id}" name="key" type="text" values="\${key}" size="20"/></td>
	           		 </tr>
				</script>
				
			<table  class="table table-bordered table-striped responsive" border="0">
				<thead>
					<tr>
						<th>请选择</th>
						<th>支付渠道</th>
						<th>支付类型</th>
						<th>支付渠道比例：(默认为1，0为不可用)</th>
						<th>支付渠道key</th>
					</tr>
				</thead>
				<tbody id="serverChannelList">
					
				</tbody>
				<tbody >
					 <tr >
						 <td colspan="5" ><input type="button" name="add" onClick="saveServerChannel();" value="保存"></td>
					  </tr>
				</tbody>
			  
			  </table>
			</div>
		</div>
	
	
	
		<div id="MyDiv" style="width:600px;display: none;	position: absolute;	top: 5%;left: 25%;border: 1px solid lightblue;background-color:FFFFFF;z-index:1002;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onClick="CloseDiv('MyDiv','fade');">x</button>
			<div class="modal-header" style="background:#F5F5F5;">
			
			<table class="table table-bordered table-striped responsive" border="0">
			<form id="saveForm" name="saveForm" action="">
			
			  <tr>
				<td>服务器名称</td>
				<td><input id="s_id" name="s_id" type="hidden" /> <input id="s_name" name="s_name" type="text"></td>
			  </tr>
			  <input id="s_ip" name="s_ip" type="hidden">
			  <!-- 
			  <tr>
				 <td>ip</td>
				<td> </td>
			  </tr>
			   <tr>
				 <td>端口</td>
				<td> </td>
			  </tr>
			   -->
			  <input id="s_port" name="s_port" type="hidden" value="0">
			  <tr>
				 <td>回调url</td>
				<td> <input id="s_call_payment_type" name="s_call_payment_type" type="hidden" value="0" /><input id="s_call_payment_url" name="s_call_payment_url" type="text"  size="50"></td>
			  </tr>
			   <tr>
				 <td>服务器验证key</td>
				<td> <textarea  id="s_key" name="s_key"  cols="30" rows="10"></textarea></td>
			  </tr>
			   <tr>
				 <td>沙箱模式</td>
				<td> <select id="isSandbox" name="isSandbox">
				  <option value="0">正式模式</option>
				  <option value="1">沙箱模式</option>
				</select>
				</td>
			  </tr>
			  
			   <tr>
				 <td>状态</td>
				<td> <select id="s_status" name="s_status">
				  <option value="2">审核通过</option>
				  <option value="3">下架</option>
				</select>
				</td>
			  </tr>
			  
			  
			  <tr >
				 <td colspan="2" ><input type="button" name="add" onClick="save();" value="保存"></td>
			  </tr>
			  </form> 
			</table>

			</div>
			
		</div>
		
		<jsp:include page="hidAllDiv.jsp"></jsp:include>
	</div>
	
	
	
		
	<script src="<%=path %>/js/pay/server.js"></script>
	
</body>
</html>