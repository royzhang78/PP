package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_18 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    $out.print($txt_1, $txt_1_bytes);
    $out.print($txt_2, $txt_2_bytes);
    $out.print($txt_3, $txt_3_bytes);
    $out.print($txt_4, $txt_4_bytes);
    $out.print($txt_5, $txt_5_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.18";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tupdate t_pay_main_retry a set `status`=1 , exe_time=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#exe";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_time";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = " ,call_server_count=call_server_count+1  \n\t\twhere  `status`=0   and call_server_count<=5\n    ";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
}
