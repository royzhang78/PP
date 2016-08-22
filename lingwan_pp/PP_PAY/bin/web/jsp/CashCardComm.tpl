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
	<script>
	function toGAME(){
	
	}
	
	function callurl(url){
		window.location=url	
	}
	</script>
</head>

<body>
	<div data-role="page" id="div_more">
		 <table width="100%" data-role="header" data-position="fixed" class="ui-header ui-bar-a ui-header-fixed slidedown">
		      <tr  >
		        <td align="left"  ><div> <img src="http://site.rodcell.com/pp/image/logo.png" width="111px" height="42px"> </div></td>
		        <td align="right" > <img src="http://site.rodcell.com/pp/image/Back.png" width="50px" height="20px"  onclick="javascript:cashcardclose()"></td>
		      </tr>
		</table>
		<div data-role="content">			
				
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
					
					#for (Map map : showlist)
					 	<tr>
					 		<td width="5%" align="right" >
					 			
					 		</td>
    						<td>
								<!--<Input  Type="button" name="submitbtn" value="$!{map.showname.asString()}" >-->
							#if (map.showimg != null)  
								<span data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="c" data-disabled="false" class="ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c" aria-disabled="false">
					 				<img src="$!{map.showimg.asString()}" onclick="javascript:callurl('$!{map.callurl.asString()}$!{urlpar.asString()}')"/>
					 			<span style="height:50px; line-height:50px; text-align:center; "> </span> </span>
					 		#end
					 		
							</td>
  						</tr>
					#end
						<tr>
							<td  width="5%">
					 			&nbsp;
					 		</td>
					 		<td>
					 			 
			
					 		</td>
					 	</tr>
				</table>
				 
				
	</div>

 
</body>
</html>