<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>Passport</title>

	<link rel="stylesheet" href="http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css" type="text/css" />

	<script type="text/javascript" src="http://site.rodcell.com/pp/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js"/></script>
	<script type="text/javascript" src="http://site.rodcell.com/pp/js/cashcardcomm.js"></script>
	<!--
	<script type="text/javascript" src="http://127.0.0.1:8080/js/pay.js"></script>
	-->
	<style>
	.error { color: #C00; }
	</style>
	
	function toGAME(){
	
	}
</head>

<body onload="com_rodcell_droid.com_rodcell_disableKeyBack()">
	<div data-role="page" id="div_more">
		 <table width="100%" data-role="header" data-position="fixed" class="ui-header ui-bar-a ui-header-fixed slidedown">
		      <tr>
		        <td align="left"  ><div> <img src="http://site.rodcell.com/pp/image/logo.png" width="111px" height="42px"> </div></td>
		        <td align="right" > <img src="http://site.rodcell.com/pp/image/Back.png" width="50px" height="20px"  onclick="javascript:cashcardclose()"></td>
		      </tr>
		</table>
		<div data-role="content">			
				<li data-role="collapsible" data-split-theme="b" id="yeepay" data-collapsed="false">
					
					<label for="tmcc14digits">id:$!{pay_id.asString()}</label>
					
					
					#if (resp_code != null && resp_code!='0')  
					 	<label for="resp_code">resp_code:$!{resp_code.asString()}</label>&nbsp; 
					#end
					<!--
					<img id='showimg' name='showimg' width="100px" height="100px" src="http://site.rodcell.com/pp/image/loading.gif"/>
					-->
					<label id="desc"></label>
					#if (resp_desc != null && resp_desc!='')  
					 	<label for="resp_code">resp_desc:$!{resp_desc.asString()}</label>
					#end
				</li>
			
	</div>

 
</body>
<script>  

  
</script>
</html>