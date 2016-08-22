package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_44 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_7, $txt_7_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.44";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n    \tinsert into t_sms_sptable\n    \t(\n\t\t\tspid,\n\t\t\tspname,\n\t\t\tenable,\n\t\t\tpercentage,\n    \t)values(\n    \t\t";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#spid";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "#";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = ",\n\t\t\t";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = "#spname";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_7 = ",\n\t\t\t1,\n\t\t\t1,\n    \t)\n    \t\n    ";
  private static final byte[] $txt_7_bytes = JetUtils.asBytes($txt_7, $ENC);
}
