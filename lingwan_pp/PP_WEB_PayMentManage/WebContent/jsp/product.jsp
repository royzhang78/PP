
<%@page import="com.tp.comm.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%String path = request.getContextPath();%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="head.jsp"></jsp:include>
<script >
var sid="<%= StringUtil.toString(request.getParameter("sid"))%>";
</script>
<body>

	<div class="box-header grd-white corner-top">


		<div class="header-control">
		<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th width="33%"><div align="right">物品标识
				          <input type="text" id="find_product_name" name="product_name" >
					  </div></th>
						<th width="33%"><input type="button" name="add" onClick="pageshow(1);" value="查询"></th>
						<th width="33%"> </th>
						<th width="33%"> </th>
					</tr>
				</thead>
			
		      </table>
		</div>
		<div class="header-control"> 
		<input type="button" name="add" onClick="rt();ShowDiv('MyDiv','fade');" value="增加物品">
		<input type="button" name="add" onClick="history.go(-1);" value="返回">
		</div>
		<div class="box-body">
			<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th>物品标识</th>
						<th>物品点数</th>
						<th>物品价格</th>
						<th>货币种类</th>
						<th>创建日期</th>
						<th>物品状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<script id="prodectTmpl" type="text/x-jquery-tmpl">
	        		<tr class="odd gradeA">
	           			<td >\${product_name}</td>

	            		<td >\${product_point}</td>
	            		<td >\${product_price}</td>
						<td >\${product_currency}</td>
						<td >\${product_create_time}</td>
						<td >\${showstatus(product_status)}</td>
						<td ><input type="button" name="add" onClick="javascript:updateshow('\${product_id}');" value="修改"></td>
	           		 </tr>
				</script>
				
				
				<tbody id="productlist">
					
					
				

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
				<td>物品标识</td>
				<td><input id="product_id" name="product_id" type="hidden" /> <input id="product_name" name="product_name" type="text"></td>
			  </tr>
			  
			   <tr>
				<td>物品名称</td>
				<td><input id="product_showname" name="product_showname" type="text"></td>
			  </tr>
			  <tr>
				 <td>自定义物品类型</td>
				<td> <input id="product_type" name="product_type" type="text"></td>
			  </tr>
			   <tr>
				 <td>物品点数</td>
				<td> <input id="product_point" name="product_point" type="text"></td>
			  </tr>
			  <tr>
				 <td>物品价格</td>
				<td> <input id="product_price" name="product_price" type="text"></td>
			  </tr>
			  
			   <tr>
				 <td>物品状态</td>
				<td> 
				<select id="product_status" name="product_status">
				<option value="2">审核通过</option>
				<option value="3">下架</option>
				</select>
				</td>
			  </tr>
			  
			  
			   <tr>
				 <td>货币种类</td>
				<td>  
				<select id="product_currency" name="product_currency">
				<jsp:include page="currency.jsp"></jsp:include>
				</select>
				
				
				<input id="product_parameter" name="product_parameter" type="hidden"></td>
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
	
	<script src="<%=path %>/js/pay/product.js"></script>
	
</body>
</html>