package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_6 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_7, $txt_7_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_9, $txt_9_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.6";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\t  select * from sys_pay_config_channel_price m WHERE m.pricepoin=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#product";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_price";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = " and m.currency=";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_7 = "_currency";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
  private static final String $txt_9 = " and `status`=2\n    ";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
}
