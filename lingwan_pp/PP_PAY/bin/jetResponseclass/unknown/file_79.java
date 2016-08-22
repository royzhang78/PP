package unknown;

import java.util.*;
import jetbrick.template.JetContext;
import jetbrick.template.runtime.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public final class file_79 extends JetPage {

  @Override
  public void render(final JetPageContext $ctx) throws Throwable {
    final JetContext context = $ctx.getContext();
    final JetWriter $out = $ctx.getWriter();
    Object responseStatus = (Object) context.get("responseStatus");
    $out.print($txt_1, $txt_1_bytes);
    $out.print(JetUtils.asEscapeHtml(JetMethods.asString(responseStatus))); // line: 2
    $out.print($txt_2, $txt_2_bytes);
    $out.flush();
  }

  @Override
  public String getName() {
    return "/unknown/file.79";
  }

  public static final String $ENC = "utf-8";
  private static final String $txt_1 = "\n\t\t";
  private static final byte[] $txt_1_bytes = JetUtils.asBytes($txt_1, $ENC);
  private static final String $txt_2 = "\n    ";
  private static final byte[] $txt_2_bytes = JetUtils.asBytes($txt_2, $ENC);
}
