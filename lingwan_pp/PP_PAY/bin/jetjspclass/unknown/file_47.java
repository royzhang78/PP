package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_47 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object showlist = (Object) context.get("showlist");
    Object urlpar = (Object) context.get("urlpar");
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    JetForIterator $for_4 = new JetForIterator(showlist);
    while ($for_4.hasNext()) { // line: 39
      Map map = (Map) $for_4.next();
      $out.print($txt_5, $txt_5_bytes);
      $out.print(JetUtils.asEscapeHtml(JetMethods.asString(map.get("showname")))); // line: 45
      $out.print($txt_6, $txt_6_bytes);
      if (JetUtils.asNotEquals(map.get("showimg"),null)) { // line: 46
        $out.print($txt_7, $txt_7_bytes);
        $out.print(JetUtils.asEscapeHtml(JetMethods.asString(map.get("showimg")))); // line: 48
        $out.print($txt_8, $txt_8_bytes);
        $out.print(JetUtils.asEscapeHtml(JetMethods.asString(map.get("callurl")))); // line: 48
        $out.print(JetUtils.asEscapeHtml(JetMethods.asString(urlpar))); // line: 48
        $out.print($txt_9, $txt_9_bytes);
      }
      $out.print($txt_10, $txt_10_bytes);
    }
    $out.print($txt_11, $txt_11_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.47";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "<html>\n<head>\n\t<meta charset=\"UTF-8\">\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n\t<title>Passport</title>\n\n\t<link rel=\"stylesheet\" href=\"http://site.rodcell.com/pp/css/jquery.mobile-1.3.0.min.css\" type=\"text/css\" />\n\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery-1.7.2.min.js\"></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/jquery.mobile-1.3.0.min.js\"/></script>\n\t<script type=\"text/javascript\" src=\"http://site.rodcell.com/pp/js/cashcardcomm.js\"></script>\n\t\n\t<style>\n\t.error { color: ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#C00";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "; }\n\t</style>\n\t<script>\n\tfunction toGAME(){\n\t\n\t}\n\t\n\tfunction callurl(url){\n\t\twindow.location=url\t\n\t}\n\t</script>\n</head>\n\n<body>\n\t<div data-role=\"page\" id=\"div_more\">\n\t\t <table width=\"100%\" data-role=\"header\" data-position=\"fixed\" class=\"ui-header ui-bar-a ui-header-fixed slidedown\">\n\t\t      <tr  >\n\t\t        <td align=\"left\"  ><div> <img src=\"http://site.rodcell.com/pp/image/logo.png\" width=\"111px\" height=\"42px\"> </div></td>\n\t\t        <td align=\"right\" > <img src=\"http://site.rodcell.com/pp/image/Back.png\" width=\"50px\" height=\"20px\"  onclick=\"javascript:cashcardclose()\"></td>\n\t\t      </tr>\n\t\t</table>\n\t\t<div data-role=\"content\">\t\t\t\n\t\t\t\t\n\t\t\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t\t\n";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_5 = "\t\t\t\t\t \t<tr>\n\t\t\t\t\t \t\t<td width=\"5%\" align=\"right\" >\n\t\t\t\t\t \t\t\t\n\t\t\t\t\t \t\t</td>\n    \t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<!--<Input  Type=\"button\" name=\"submitbtn\" value=\"";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "\" >-->\n";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_7 = "\t\t\t\t\t\t\t\t<span data-corners=\"true\" data-shadow=\"true\" data-iconshadow=\"true\" data-wrapperels=\"span\" data-theme=\"c\" data-disabled=\"false\" class=\"ui-btn ui-shadow ui-btn-corner-all ui-btn-up-c\" aria-disabled=\"false\">\n\t\t\t\t\t \t\t\t\t<img src=\"";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
  private static final String $txt_8 = "\" onclick=\"javascript:callurl(\'";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_9 = "\')\"/>\n\t\t\t\t\t \t\t\t<span style=\"height:50px; line-height:50px; text-align:center; \"> </span> </span>\n";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
  private static final String $txt_10 = "\t\t\t\t\t \t\t\n\t\t\t\t\t\t\t</td>\n  \t\t\t\t\t\t</tr>\n";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
  private static final String $txt_11 = "\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td  width=\"5%\">\n\t\t\t\t\t \t\t\t&nbsp;\n\t\t\t\t\t \t\t</td>\n\t\t\t\t\t \t\t<td>\n\t\t\t\t\t \t\t\t \n\t\t\t\n\t\t\t\t\t \t\t</td>\n\t\t\t\t\t \t</tr>\n\t\t\t\t</table>\n\t\t\t\t \n\t\t\t\t\n\t</div>\n\n \n</body>\n</html>\n";
  private static final byte[] $txt_11_bytes = JetUtils.asBytes($txt_11, $ENC);
}
