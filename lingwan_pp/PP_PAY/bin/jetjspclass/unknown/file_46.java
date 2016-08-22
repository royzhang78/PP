package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_46 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object pay_id = (Object) context.get("pay_id");
    Object resp_code = (Object) context.get("resp_code");
    Object resp_desc = (Object) context.get("resp_desc");
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print(JetUtils.asEscapeHtml(JetMethods.asString(pay_id))); // line: 35
    $out.print($txt_4, $txt_4_bytes);
    if ((JetUtils.asNotEquals(resp_code,null)&&JetUtils.asNotEquals(resp_code,"0"))) { // line: 38
      $out.print($txt_5, $txt_5_bytes);
      $out.print(JetUtils.asEscapeHtml(JetMethods.asString(resp_code))); // line: 39
      $out.print($txt_6, $txt_6_bytes);
    }
    $out.print($txt_7, $txt_7_bytes);
    if ((JetUtils.asNotEquals(resp_desc,null)&&JetUtils.asNotEquals(resp_desc,""))) { // line: 45
      $out.print($txt_8, $txt_8_bytes);
      $out.print(JetUtils.asEscapeHtml(JetMethods.asString(resp_desc))); // line: 46
      $out.print($txt_9, $txt_9_bytes);
    }
    $out.print($txt_10, $txt_10_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.46";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "<html>\n<head>\n\t<meta charset=\"UTF-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n\t<title>Passport</title>\n\n\t<link rel=\"stylesheet\" href=\"http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css\" type=\"text/css\" />\n\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery-1.7.2.min.js\"></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js\"/></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/cashcardcomm.js\"></script>\n\t<!--\n\t<script type=\"text/javascript\" src=\"http://127.0.0.1:8080/js/pay.js\"></script>\n\t-->\n\t<style>\n\t.error { color: ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#C00";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "; }\n\t</style>\n\t\n\tfunction toGAME(){\n\t\n\t}\n</head>\n\n<body onload=\"com_rodcell_droid.com_rodcell_disableKeyBack()\">\n\t<div data-role=\"page\" id=\"div_more\">\n\t\t <table width=\"100%\" data-role=\"header\" data-position=\"fixed\" class=\"ui-header ui-bar-a ui-header-fixed slidedown\">\n\t\t      <tr>\n\t\t        <td align=\"left\"  ><div> <img src=\"http://site.rodcell.com/pp/image/logo.png\" width=\"111px\" height=\"42px\"> </div></td>\n\t\t        <td align=\"right\" > <img src=\"http://site.rodcell.com/pp/image/Back.png\" width=\"50px\" height=\"20px\"  onclick=\"javascript:cashcardclose()\"></td>\n\t\t      </tr>\n\t\t</table>\n\t\t<div data-role=\"content\">\t\t\t\n\t\t\t\t<li data-role=\"collapsible\" data-split-theme=\"b\" id=\"yeepay\" data-collapsed=\"false\">\n\t\t\t\t\t\n\t\t\t\t\t<label for=\"tmcc14digits\">id:";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "</label>\n\t\t\t\t\t\n\t\t\t\t\t\n";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "\t\t\t\t\t \t<label for=\"resp_code\">resp_code:";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "</label>&nbsp; \n";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_7 = "\t\t\t\t\t<!--\n\t\t\t\t\t<img id=\'showimg\' name=\'showimg\' width=\"100px\" height=\"100px\" src=\"http://site.rodcell.com/pp/image/loading.gif\"/>\n\t\t\t\t\t-->\n\t\t\t\t\t<label id=\"desc\"></label>\n";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
  private static final String $txt_8 = "\t\t\t\t\t \t<label for=\"resp_code\">resp_desc:";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_9 = "</label>\n";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
  private static final String $txt_10 = "\t\t\t\t</li>\n\t\t\t\n\t</div>\n\n \n</body>\n<script>  \n\n  \n</script>\n</html>\n";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
}
