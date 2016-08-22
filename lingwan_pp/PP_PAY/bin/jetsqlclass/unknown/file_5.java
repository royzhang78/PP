package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_5 extends JetPage {

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
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_8, $txt_8_bytes);
    $out.print($txt_9, $txt_9_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_11, $txt_11_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_6, $txt_6_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_15, $txt_15_bytes);
    $out.print($txt_9, $txt_9_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_18, $txt_18_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.5";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tselect * from sys_pay_config_channel_price \n\t\twhere (pricepoin=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#pricepoin";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "#";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "\n\t\tand channel_type=";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "#channel";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "_type";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_8 = "\n\t\tand currency=";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_9 = "#currency";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
  private static final String $txt_11 = " and type=0) \n\t\tor \n\t\t(channel_type=";
  private static final byte[] $txt_11_bytes = JetUtils.asBytes($txt_11, $ENC);
  private static final String $txt_15 = " \n\t\tand currency=";
  private static final byte[] $txt_15_bytes = JetUtils.asBytes($txt_15, $ENC);
  private static final String $txt_18 = " \n\t\tand type=1)\n\t\t\n    ";
  private static final byte[] $txt_18_bytes = JetUtils.asBytes($txt_18, $ENC);
}
