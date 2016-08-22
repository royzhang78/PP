package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_34 extends JetPage {

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
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.34";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n    \tselect b.showimg,b.callurl,b.channel_id,a.product_currency,a.product_price,(select group_concat(pricepoin separator \',\') from sys_pay_config_channel_price p  where b.channel_id=p.channel_type and b.currency=p.currency) as pricepoin from t_pay_product a ,t_pay_channel  b where a.product_status=\'2\' and  b.`enable`=1 and  a.product_currency=B.currency and a.sid=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#sid";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "#";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = " AND A.product_name=";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "#product";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "_name";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_8 = " \n    \tand b.type=2 and ((\n\t\tSELECT\n\t\t\tgroup_concat(pricepoin SEPARATOR \',\')\n\t\tFROM\n\t\t\tsys_pay_config_channel_price p\n\t\tWHERE\n\t\t\tb.channel_id = p.channel_type\n\t\tAND b.currency = p.currency\n\t\tand p.pricepoin=a.product_price\n\t) is not null or (product_currency=\'Chips\' AND B.currency=\'Chips\')\n)   GROUP BY  b.showimg,\n\tb.callurl,\n\tb.channel_id,\n\ta.product_currency,\n\ta.product_price, pricepoin\n\t\t ";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
}
