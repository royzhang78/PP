package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_48 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object message = (Object) context.get("message");
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print(JetUtils.asEscapeHtml(JetMethods.asString(message))); // line: 33
    $out.print($txt_4, $txt_4_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.48";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "<html>\n<head>\n\t<meta charset=\"UTF-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n\t<title>Passport</title>\n\n\t<link rel=\"stylesheet\" href=\"http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css\" type=\"text/css\" />\n\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery-1.7.2.min.js\"></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js\"/></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/cashcardcomm.js\"></script>\n\t\n\t<style>\n\t.error { color: ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#C00";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "; }\n\t</style>\n\t\n\tfunction toGAME(){\n\t\n\t}\n</head>\n\n<body>\n\t<div data-role=\"page\" id=\"div_more\">\n\t\t <table width=\"100%\" data-role=\"header\" data-position=\"fixed\" class=\"ui-header ui-bar-a ui-header-fixed slidedown\">\n      <tr  >\n        <td align=\"left\"  ><div> <img src=\"http://site.rodcell.com/pp/image/logo.png\" width=\"111px\" height=\"42px\"> </div></td>\n        <td align=\"right\" > <img src=\"http://site.rodcell.com/pp/image/Back.png\" width=\"50px\" height=\"20px\" onclick=\"javascript:cashcardreturn()\"></td>\n      </tr>\n</table>\n\t\t<div data-role=\"content\">\t\t\t\n\t\t\t\t<li data-role=\"collapsible\" data-split-theme=\"b\" id=\"yeepay\" data-collapsed=\"false\">\n\t\t\t\t\t\n\t\t\t\t\t<label for=\"tmcc14digits\">";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "</label>\n\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t</li>\n\t\t\t\n\t</div>\n\n \n</body>\n</html>\n";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
}
