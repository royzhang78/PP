package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_36 extends JetPage {

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
    return "/unknown/file.36";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tselect * from t_pay_server_channel where c_id=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#c";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_id";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = " and s_id=";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
  private static final String $txt_6 = "#s";
  private static final byte[] $txt_6_bytes = JetUtils.asBytes($txt_6, $ENC);
  private static final String $txt_9 = " and status=2\n    ";
  private static final byte[] $txt_9_bytes = JetUtils.asBytes($txt_9, $ENC);
}
