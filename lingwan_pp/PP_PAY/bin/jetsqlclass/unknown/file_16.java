package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_16 extends JetPage {

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
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_9, $txt_9_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_12, $txt_12_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_14, $txt_14_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.16";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tinsert into t_pay_main_error (\n\t\t\t\tpay_id,\n\t\t\t\ttype,\n\t\t\t\ttext,\n\t\t\t\terrordate\n\t\t) values(\t\t\n\t\t\t";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#pay";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_id";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = ",\n\t\t\t";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "#type";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_9 = "#text";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
  private static final String $txt_12 = "#errordate";
  private static final byte[] $txt_12_bytes = JetUtils.asBytes($txt_12, $ENC);
  private static final String $txt_14 = "\n\t\t)\n    ";
  private static final byte[] $txt_14_bytes = JetUtils.asBytes($txt_14, $ENC);
}
