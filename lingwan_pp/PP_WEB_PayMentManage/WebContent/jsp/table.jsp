
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="head.jsp"></jsp:include>
<body>

	<div class="box-header grd-white corner-top">


		<div class="header-control">

			<a data-box="collapse"><i class="icofont-caret-up"></i></a> <a
				data-box="close" data-hide="bounceOutRight">&times;</a> <span>Datatables</span>
			<input type="button" name="add" onclick="ShowDiv('MyDiv','fade');" value="增加">
		</div>
		
		<div class="box-body">
			<table id="datatables"
				class="table table-bordered table-striped responsive">
				<thead>
					<tr>
						<th>Rendering engine</th>
						<th>Browser</th>
						<th>Platform(s)</th>
						<th>Engine version</th>
						<th>CSS grade</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Trident</td>
						<td>Internet Explorer 4.0</td>
						<td>Win 95+</td>
						<td class="center">4</td>
						<td class="center">X</td>
					</tr>
					<tr>
						<td>Trident</td>
						<td>Internet Explorer 5.0</td>
						<td>Win 95+</td>
						<td class="center">5</td>
						<td class="center">C</td>
					</tr>
					<tr>
						<td>Trident</td>
						<td>Internet Explorer 5.5</td>
						<td>Win 95+</td>
						<td class="center">5.5</td>
						<td class="center">A</td>
					</tr>
					<tr class="even gradeA">
						<td>Trident</td>
						<td>Internet Explorer 6</td>
						<td>Win 98+</td>
						<td class="center">6</td>
						<td class="center">A</td>
					</tr>
					<tr class="odd gradeA">
						<td>Trident</td>
						<td>Internet Explorer 7</td>
						<td>Win XP SP2+</td>
						<td class="center">7</td>
						<td class="center">A</td>
					</tr>
				

				</tbody>
			</table>

		</div>
		
		<div class="page" id="page5"></div>
		
		<div id="fade" class="black_overlay"></div>
		<div id="MyDiv" style="width:600px;display: none;	position: absolute;	top: 5%;left: 25%;border: 1px solid lightblue;background-color:FFFFFF;z-index:1002;">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseDiv('MyDiv','fade');">x</button>
			<div class="modal-header" style="background:#F5F5F5;">
			ok
			</div>
			
		</div>
		
		
	</div>
	<script type="text/javascript">
		function pageshow(num) {
			//选择器调用方式指定样式 style 可选big  和 small，可自定义首页尾页按钮的文本
			$("#page5").mypage({
				now : num,
				max : 25,
				callback : function(page) {
					pageshow(page);
				},
				style : "small",
				first : "First",
				last : "Last"
			});
		}
		pageshow(0);
	</script>
</body>
</html>