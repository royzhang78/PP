package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_32 extends JetPage {

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
    return "/unknown/file.32";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tselect * from t_pay_product where sid=(select s_id from t_pay_server where s_name=";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "#s";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
  private static final String $txt_3 = "_name";
  private static final byte[] $txt_3_bytes = JetUtils.asBytes($txt_3, $ENC);
  private static final String $txt_4 = "#";
  private static final byte[] $txt_4_bytes = JetUtils.asBytes($txt_4, $ENC);
  private static final String $txt_5 = ") and product_status=2\n    ";
  private static final byte[] $txt_5_bytes = JetUtils.asBytes($txt_5, $ENC);
}
