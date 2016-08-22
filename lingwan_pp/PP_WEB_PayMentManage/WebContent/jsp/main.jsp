
 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
    <%
	String path = request.getContextPath();
	%>
    
        <meta charset="utf-8" />
        <title>pay ment</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="description" content="" />
        <meta name="author" content="stilearning" />

        <!-- google font -->
        <link href="<%=path %>/css/css.css" rel="stylesheet" type="text/css" />

        <!-- styles -->
        <link href="<%=path %>/css/bootstrap.css" rel="stylesheet" />
        <link href="<%=path %>/css/bootstrap-responsive.css" rel="stylesheet" />
        <link href="<%=path %>/css/stilearn.css" rel="stylesheet" />
        <link href="<%=path %>/css/stilearn-responsive.css" rel="stylesheet" />
        <link href="<%=path %>/css/stilearn-helper.css" rel="stylesheet" />
        <link href="<%=path %>/css/stilearn-icon.css" rel="stylesheet" />
        <link href="<%=path %>/css/font-awesome.css" rel="stylesheet" />
        <link href="<%=path %>/css/animate.css" rel="stylesheet" />
        <link href="<%=path %>/css/uniform.default.css" rel="stylesheet" />
        
        <link href="<%=path %>/css/DT_bootstrap.css" rel="stylesheet" />
        <link href="<%=path %>/css/responsive-tables.css" rel="stylesheet" />
        
        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

    <body>
        <!-- section header -->
        <header class="header" data-spy="affix" data-offset-top="0">
            <!--nav bar helper-->
            <div class="navbar-helper">
                <div class="row-fluid">
                    <!--panel site-name-->
                    <div class="span2">
                        <div class="panel-sitename">
                            <h2><a href="index.html"><span class="color-teal">rod</span>cell</a></h2>
                        </div>
                    </div>
                    <!--/panel name-->
                </div>
            </div><!--/nav bar helper-->
        </header>

        <!-- section content -->
        <section class="section">
            <div class="row-fluid">
                <!-- span side-left -->
                <div class="span1">
                    <!--side bar-->
                    <aside class="side-left">
                        <ul class="sidebar">
                        <!-- 
						<li id="li1"><a href="#"
							onclick="javascript:toUrl('li1','product.jsp');" title="dashboard">
								<span class="sidebar-text">游戏物品</span>
						</a></li> 
						-->
						<li class="active" id="li2"><a href="#" onclick="javascript:toUrl('li2','server.jsp');"
							title="dashboard"> <span class="sidebar-text">游戏服务器</span>
						</a></li>
						<li id="li3"><a href="#" onclick="javascript:toUrl('li3','configChannelPrice.jsp');"
							title="dashboard"> <span class="sidebar-text">渠道价格设定</span>
						</a></li>


					</ul>
                    </aside><!--/side bar -->
                </div><!-- span side-left -->
                
                <!-- span content -->
                <div class="span11">
                    <!-- content -->
                    <div class="content">
                        <!-- content-header -->
                       
						
                        
                        <!-- content-breadcrumb -->
                       
 						
                        <div id="fade" class="black_overlay"></div>
                        <!-- content-body -->
                        <div class="content-body">
                        
							<iframe frameborder="0" src="<%=path %>/jsp/server.jsp"  name="home" id="home"  width="100%" height="800px"></iframe>
                           
                            <!--/tables-->
                        </div><!--/content-body -->
                    </div><!-- /content -->
                </div><!-- /span content -->
                
             

        <!-- section footer -->
        <footer>
            <a rel="to-top" href="#top"><i class="icofont-circle-arrow-up"></i></a>
        </footer>

        <!-- javascript
        ================================================== 
        <script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script>-->
        <script src="<%=path %>/js/jquery.js"></script>
        <script src="<%=path %>/js/bootstrap.js"></script>
        <script src="<%=path %>/js/uniform/jquery.uniform.js"></script>
        
        <script src="<%=path %>/js/datatables/jquery.dataTables.min.js"></script>
        <script src="<%=path %>/js/datatables/extras/ZeroClipboard.js"></script>
        <script src="<%=path %>/js/datatables/extras/TableTools.min.js"></script>
        <script src="<%=path %>/js/datatables/DT_bootstrap.js"></script>
        <script src="<%=path %>/js/responsive-tables/responsive-tables.js"></script>
        
        <!-- required stilearn template js, for full feature-->
        <script src="js/holder.js"></script>
        <script src="js/stilearn-base.js"></script>

        <script type="text/javascript">
            $(document).ready(function() {
                // try your js
                
                // uniform
                $('[data-form=uniform]').uniform();
                
                // datatables
                $('#datatables').dataTable( {
                    "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                    "sPaginationType": "bootstrap",
                    "oLanguage": {
                            "sLengthMenu": "_MENU_ records per page"
                    }
                });
                
                // datatables table tools
                $('#datatablestools').dataTable({
                    "sDom": "<'row-fluid'<'span6'T><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                    "oTableTools": {
                        "aButtons": [
                            "copy",
                            "print",
                            {
                                "sExtends":    "collection",
                                "sButtonText": 'Save <span class="caret" />',
                                "aButtons":    [ 
                                    "xls", 
                                    "csv",
                                    {
                                        "sExtends": "pdf",
                                        "sPdfOrientation": "landscape",
                                        "sPdfMessage": "Your custom message would go here."
                                    }
                                ]
                            }
                        ],
                        "sSwfPath": "<%=path %>/js/datatables/swf/copy_csv_xls_pdf.swf"
                    }
                });
            });
      
            function toUrl(activeid, url){
            	$(".active").removeClass("active")
            	document.getElementById("home").src="../jsp/"+url;
            	$("#"+activeid).addClass("active");
            }
        </script>
    </body>
</html>
