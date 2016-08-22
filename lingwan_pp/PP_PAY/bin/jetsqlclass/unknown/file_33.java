package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_33 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_6, $txt_6_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_8, $txt_8_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_10, $txt_10_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_12, $txt_12_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.33";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tselect * from t_pay_product where product_name=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#product";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_name";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = " and sid=";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "#sid";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_8 = " and product_currency=";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_10 = "_currency";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
  private static final String $txt_12 = "\n    ";
  private static final byte[] $txt_12_bytes = JetUtils.asBytes($txt_12, $ENC);
}
