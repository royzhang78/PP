package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_11 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    $out.print($txt_1, $txt_1_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.11";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\tselect * from sys_pay_static_config\n    ";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
}
