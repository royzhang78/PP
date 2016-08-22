package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_43 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object spid = (Object) context.get("spid");
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_4, $txt_4_bytes);
    if (JetUtils.asEquals(spid,null)) { // line: 6
      $out.print($txt_5, $txt_5_bytes);
    }
    else { // line: 8
      $out.print($txt_6, $txt_6_bytes);
      $out.print($txt_7, $txt_7_bytes);
      $out.print($txt_8, $txt_8_bytes);
      $out.print($txt_7, $txt_7_bytes);
      $out.print($txt_10, $txt_10_bytes);
    }
    $out.print($txt_11, $txt_11_bytes);
    $out.print($txt_12, $txt_12_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_14, $txt_14_bytes);
    $out.print($txt_15, $txt_15_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_17, $txt_17_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.43";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n    select w.*,e.percentage from t_sms_detail w ,t_sms_optable d,t_sms_sptable e where  \n\tw.operatorid =d.operatorid \n\tand  w.spid=e.spid \n\tand d.plmn=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#plmn";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "#";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "\n";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "\t\tand\t1=1\t\n";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "\t\tand w.id in (";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_7 = "$";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
  private static final String $txt_8 = "spid";
  private static final byte[] $txt_8_bytes = JetUtils.asBytes($txt_8, $ENC);
  private static final String $txt_10 = ")\n";
  private static final byte[] $txt_10_bytes = JetUtils.asBytes($txt_10, $ENC);
  private static final String $txt_11 = "\tand cast(w.price as decimal(10,2))=cast(";
  private static final byte[] $txt_11_bytes = JetUtils.asBytes($txt_11, $ENC);
  private static final String $txt_12 = "#price";
  private static final byte[] $txt_12_bytes = JetUtils.asBytes($txt_12, $ENC);
  private static final String $txt_14 = " as decimal(10,2)) and w.currency=";
  private static final byte[] $txt_14_bytes = JetUtils.asBytes($txt_14, $ENC);
  private static final String $txt_15 = "#currency";
  private static final byte[] $txt_15_bytes = JetUtils.asBytes($txt_15, $ENC);
  private static final String $txt_17 = " and e.`enable`=1 and w.`enable`=1\n\t\n    ";
  private static final byte[] $txt_17_bytes = JetUtils.asBytes($txt_17, $ENC);
}
