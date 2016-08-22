<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>Passport</title>

	<link rel="stylesheet" href="http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css" type="text/css" />

	<script type="text/javascript" src="http://site.rodcell.com/pp/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js"/></script>
	<script type="text/javascript" src="http://site.rodcell.com/pp/js/cashcardcomm.js"></script>

	<style>
	.error { color: #C00; }
	</style>
</head>

<body>
	<div data-role="page" id="div_more">
		 <table width="100%" data-role="header" data-position="fixed" class="ui-header ui-bar-a ui-header-fixed slidedown">
		      <tr  >
		        <td align="left"  ><div> <img src="http://site.rodcell.com/pp/image/logo.png" width="111px" height="42px"> </div></td>
		        <td align="right" > <img src="http://site.rodcell.com/pp/image/Back.png" width="50px" height="20px" onclick="javascript:cashcardreturn()"></td>
		      </tr>
		</table>
		<div data-role="content">
			<form id="startForm" action="molService.do?$!{urlpar.asString()}" method="post" novalidate="novalidate">
				<li data-role="collapsible" data-split-theme="b" id="yeepay" data-collapsed="false">
					<h3>#if (isSandbox == '1') <font color="red" >Test </font>#end  $!{channelid.asString()}</h3>
					#if (channelid == '12call')  
					<span data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="c" data-disabled="false" class="ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c" aria-disabled="false"> <img src="http://site.rodcell.com/pp/image/one2call.png"> <span style="height:50px; line-height:50px; text-align:center; "> </span> </span>
					#end
					
					#if (channelid == 'molpoint')  
					<span data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="c" data-disabled="false" class="ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c" aria-disabled="false"> <img src="http://site.rodcell.com/pp/image/MOLPoints.png"> <span style="height:50px; line-height:50px; text-align:center; "> </span> </span>
					#end
					
					#if (channelid == 'zest')  
					<span data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="c" data-disabled="false" class="ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c" aria-disabled="false"> <img src="http://site.rodcell.com/pp/image/Zest.png"> <span style="height:50px; line-height:50px; text-align:center; "> </span> </span>
					#end
					
					#if (channelid == 'truemoney')  
					<span data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="c" data-disabled="false" class="ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c" aria-disabled="false"> <img src="http://site.rodcell.com/pp/image/truemoney.png"> <span style="height:50px; line-height:50px; text-align:center; "> </span> </span>
					#end
					
					#if (channelid == 'molpoint'||channelid == 'zest')  
						<label for="tmcc14digits">serial_no</label>
						<input name="serial_no" id="serial_no" value="" type="text" data-validate="required" />
					#end
					
					
					<label for="tmcc14digits">pin_no</label>
					<input name="pin_no" id="pin_no" value="" type="text" data-validate="required" />
					
					<Input Type="submit" name="submitbtn" value="nextStep" > 
				</li>
			</form>

			<div data-role="popup" id="popupDialog" data-overlay-theme="a"
				data-theme="c" data-dismissible="false" style="max-width: 400px;"
				class="ui-corner-all">
				<div data-role="content" data-theme="d" class="ui-corner-bottom ui-content">
					<h3 id="retContent" class="ui-title"></h3>
					<a id="confirmBtn" href="#" data-role="button" data-rel="back" data-transition="flow" data-theme="b">
						confirm
					</a>
				</div>
			</div>
		</div>
		<div data-role="footer" data-theme="c" data-position="fixed">
			<h4><em>&copy; </em></h4>
		</div>
	</div>

 
</body>
</html>