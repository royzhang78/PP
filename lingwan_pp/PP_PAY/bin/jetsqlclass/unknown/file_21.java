package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_21 extends JetPage {

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
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_10, $txt_10_bytes);
    $out.print($txt_11, $txt_11_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_14, $txt_14_bytes);
    $out.print($txt_15, $txt_15_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_18, $txt_18_bytes);
    $out.print($txt_11, $txt_11_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_22, $txt_22_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_25, $txt_25_bytes);
    $out.print($txt_11, $txt_11_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_29, $txt_29_bytes);
    $out.print($txt_30, $txt_30_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_29, $txt_29_bytes);
    $out.print($txt_34, $txt_34_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_37, $txt_37_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_39, $txt_39_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.21";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tinsert into t_pay_main_retry\n\t\t(  \n\t\t\tpay_id,\n\t\t\tserver_id,\n\t\t\tnext_time,\n\t\t\tcall_server_count,\n\t\t\tstart_time,\n\t\t\tstatus,\n\t\t\texe_time,\n\t\t\terror_code,\n\t\t\terror_desc,\n\t\t\ttype\n\t\t)values(\n\t \t\t";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#pay";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_id";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = ",\n\t\t\t";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "#server";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_10 = "#next";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
  private static final String $txt_11 = "_time";
  private static final byte[] $txt_11_bytes = JetUtils.asBytes($txt_11, $ENC);
  private static final String $txt_14 = "#call";
  private static final byte[] $txt_14_bytes = JetUtils.asBytes($txt_14, $ENC);
  private static final String $txt_15 = "_server_count";
  private static final byte[] $txt_15_bytes = JetUtils.asBytes($txt_15, $ENC);
  private static final String $txt_18 = "#start";
  private static final byte[] $txt_18_bytes = JetUtils.asBytes($txt_18, $ENC);
  private static final String $txt_22 = "#status";
  private static final byte[] $txt_22_bytes = JetUtils.asBytes($txt_22, $ENC);
  private static final String $txt_25 = "#exe";
  private static final byte[] $txt_25_bytes = JetUtils.asBytes($txt_25, $ENC);
  private static final String $txt_29 = "#error";
  private static final byte[] $txt_29_bytes = JetUtils.asBytes($txt_29, $ENC);
  private static final String $txt_30 = "_code";
  private static final byte[] $txt_30_bytes = JetUtils.asBytes($txt_30, $ENC);
  private static final String $txt_34 = "_desc";
  private static final byte[] $txt_34_bytes = JetUtils.asBytes($txt_34, $ENC);
  private static final String $txt_37 = "#type";
  private static final byte[] $txt_37_bytes = JetUtils.asBytes($txt_37, $ENC);
  private static final String $txt_39 = "\n\t)\n    ";
  private static final byte[] $txt_39_bytes = JetUtils.asBytes($txt_39, $ENC);
}
