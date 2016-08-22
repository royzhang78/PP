package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_49 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object urlpar = (Object) context.get("urlpar");
    Object isSandbox = (Object) context.get("isSandbox");
    Object channelid = (Object) context.get("channelid");
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print(JetUtils.asEscapeHtml(JetMethods.asString(urlpar))); // line: 27
    $out.print($txt_4, $txt_4_bytes);
    if (JetUtils.asEquals(isSandbox,"1")) { // line: 29
      $out.print($txt_5, $txt_5_bytes);
    }
    $out.print($txt_6, $txt_6_bytes);
    $out.print(JetUtils.asEscapeHtml(JetMethods.asString(channelid))); // line: 29
    $out.print($txt_7, $txt_7_bytes);
    if (JetUtils.asEquals(channelid,"12call")) { // line: 30
      $out.print($txt_8, $txt_8_bytes);
    }
    $out.print($txt_9, $txt_9_bytes);
    if (JetUtils.asEquals(channelid,"molpoint")) { // line: 34
      $out.print($txt_10, $txt_10_bytes);
    }
    $out.print($txt_9, $txt_9_bytes);
    if (JetUtils.asEquals(channelid,"zest")) { // line: 38
      $out.print($txt_12, $txt_12_bytes);
    }
    $out.print($txt_9, $txt_9_bytes);
    if (JetUtils.asEquals(channelid,"truemoney")) { // line: 42
      $out.print($txt_14, $txt_14_bytes);
    }
    $out.print($txt_9, $txt_9_bytes);
    if ((JetUtils.asEquals(channelid,"molpoint")||JetUtils.asEquals(channelid,"zest"))) { // line: 46
      $out.print($txt_16, $txt_16_bytes);
    }
    $out.print($txt_17, $txt_17_bytes);
    $out.print($txt_18, $txt_18_bytes);
    $out.print($txt_19, $txt_19_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.49";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "<html>\n<head>\n\t<meta charset=\"UTF-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n\t<title>Passport</title>\n\n\t<link rel=\"stylesheet\" href=\"http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css\" type=\"text/css\" />\n\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery-1.7.2.min.js\"></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js\"/></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/cashcardcomm.js\"></script>\n\n\t<style>\n\t.error { color: ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#C00";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "; }\n\t</style>\n</head>\n\n<body>\n\t<div data-role=\"page\" id=\"div_more\">\n\t\t <table width=\"100%\" data-role=\"header\" data-position=\"fixed\" class=\"ui-header ui-bar-a ui-header-fixed slidedown\">\n\t\t      <tr  >\n\t\t        <td align=\"left\"  ><div> <img src=\"http://site.rodcell.com/pp/image/logo.png\" width=\"111px\" height=\"42px\"> </div></td>\n\t\t        <td align=\"right\" > <img src=\"http://site.rodcell.com/pp/image/Back.png\" width=\"50px\" height=\"20px\" onclick=\"javascript:cashcardreturn()\"></td>\n\t\t      </tr>\n\t\t</table>\n\t\t<div data-role=\"content\">\n\t\t\t<form id=\"startForm\" action=\"molService.do?";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "\" method=\"post\" novalidate=\"novalidate\">\n\t\t\t\t<li data-role=\"collapsible\" data-split-theme=\"b\" id=\"yeepay\" data-collapsed=\"false\">\n\t\t\t\t\t<h3>";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = " <font color=\"red\" >Test </font>";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "  ";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_7 = "</h3>\n";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
  private static final String $txt_8 = "\t\t\t\t\t<span data-corners=\"true\" data-shadow=\"true\" data-iconshadow=\"true\" data-wrapperels=\"span\" data-theme=\"c\" data-disabled=\"false\" class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c\" aria-disabled=\"false\"> <img src=\"http://site.rodcell.com/pp/image/one2call.png\"> <span style=\"height:50px; line-height:50px; text-align:center; \"> </span> </span>\n";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_9 = "\t\t\t\t\t\n";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
  private static final String $txt_10 = "\t\t\t\t\t<span data-corners=\"true\" data-shadow=\"true\" data-iconshadow=\"true\" data-wrapperels=\"span\" data-theme=\"c\" data-disabled=\"false\" class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c\" aria-disabled=\"false\"> <img src=\"http://site.rodcell.com/pp/image/MOLPoints.png\"> <span style=\"height:50px; line-height:50px; text-align:center; \"> </span> </span>\n";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
  private static final String $txt_12 = "\t\t\t\t\t<span data-corners=\"true\" data-shadow=\"true\" data-iconshadow=\"true\" data-wrapperels=\"span\" data-theme=\"c\" data-disabled=\"false\" class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c\" aria-disabled=\"false\"> <img src=\"http://site.rodcell.com/pp/image/Zest.png\"> <span style=\"height:50px; line-height:50px; text-align:center; \"> </span> </span>\n";
  private static final byte[] $txt_12_bytes = JetUtils.asBytes($txt_12, $ENC);
  private static final String $txt_14 = "\t\t\t\t\t<span data-corners=\"true\" data-shadow=\"true\" data-iconshadow=\"true\" data-wrapperels=\"span\" data-theme=\"c\" data-disabled=\"false\" class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c\" aria-disabled=\"false\"> <img src=\"http://site.rodcell.com/pp/image/truemoney.png\"> <span style=\"height:50px; line-height:50px; text-align:center; \"> </span> </span>\n";
  private static final byte[] $txt_14_bytes = JetUtils.asBytes($txt_14, $ENC);
  private static final String $txt_16 = "\t\t\t\t\t\t<label for=\"tmcc14digits\">serial_no</label>\n\t\t\t\t\t\t<input name=\"serial_no\" id=\"serial_no\" value=\"\" type=\"text\" data-validate=\"required\" />\n";
  private static final byte[] $txt_16_bytes = JetUtils.asBytes($txt_16, $ENC);
  private static final String $txt_17 = "\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t\t<label for=\"tmcc14digits\">pin_no</label>\n\t\t\t\t\t<input name=\"pin_no\" id=\"pin_no\" value=\"\" type=\"text\" data-validate=\"required\" />\n\t\t\t\t\t\n\t\t\t\t\t<Input Type=\"submit\" name=\"submitbtn\" value=\"nextStep\" > \n\t\t\t\t</li>\n\t\t\t</form>\n\n\t\t\t<div data-role=\"popup\" id=\"popupDialog\" data-overlay-theme=\"a\"\n\t\t\t\tdata-theme=\"c\" data-dismissible=\"false\" style=\"max-width: 400px;\"\n\t\t\t\tclass=\"ui-corner-all\">\n\t\t\t\t<div data-role=\"content\" data-theme=\"d\" class=\"ui-corner-bottom ui-content\">\n\t\t\t\t\t<h3 id=\"retContent\" class=\"ui-title\"></h3>\n\t\t\t\t\t<a id=\"confirmBtn\" href=\"";
  private static final byte[] $txt_17_bytes = JetUtils.asBytes($txt_17, $ENC);
  private static final String $txt_18 = "#";
  private static final byte[] $txt_18_bytes = JetUtils.asBytes($txt_18, $ENC);
  private static final String $txt_19 = "\" data-role=\"button\" data-rel=\"back\" data-transition=\"flow\" data-theme=\"b\">\n\t\t\t\t\t\tconfirm\n\t\t\t\t\t</a>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div data-role=\"footer\" data-theme=\"c\" data-position=\"fixed\">\n\t\t\t<h4><em>&copy; </em></h4>\n\t\t</div>\n\t</div>\n\n \n</body>\n</html>\n";
  private static final byte[] $txt_19_bytes = JetUtils.asBytes($txt_19, $ENC);
}
