package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_35 extends JetPage {

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
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_9, $txt_9_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.35";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n    \t\tSELECT\n\td.product_id,\n\td.product_name,\n\td.product_showname,\n\td.product_point,\n\td.product_type,\n\td.pay_channel_type,\n\td.product_price,\n\td.product_currency,\n\td.product_parameter \n\nfrom t_pay_product d\nWHERE\n\td.sid = (\n\t\tSELECT\n\t\t\ts_id\n\t\tFROM\n\t\t\tt_pay_server s\n\t\tWHERE\n\t\t\ts.s_name = ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#s";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_name";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "\n\t)\nAND d.product_name = ";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "#product";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_9 = "\nAND product_status = 2\nORDER BY\n\tpay_channel_type\n    \t ";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
}
