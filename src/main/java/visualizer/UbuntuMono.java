package visualizer;

import java.awt.*;
import java.io.File;

public class UbuntuMono {
  private static Font[] fontArray;

  public UbuntuMono() {
      try {
          fontArray = new Font[FontStyle.values().length];
          fontArray[0] = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "fonts/ubuntu-mono/UbuntuMono-Bold.ttf"));
          fontArray[1] = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "fonts/ubuntu-mono/UbuntuMono-BoldItalic.ttf"));
          fontArray[2] = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "fonts/ubuntu-mono/UbuntuMono-Italic.ttf"));
          fontArray[3] = Font.createFont(Font.TRUETYPE_FONT, new File(
                    "fonts/ubuntu-mono/UbuntuMono-Regular.ttf"));
      } catch (Exception e) {
          System.out.println("could not load ubuntu mono font");
      }
  }

  public static Font getFont(FontStyle fontStyle, float size) {
      return fontArray[fontStyle.ordinal()].deriveFont(size);
  }

  public enum FontStyle {
      BOLD, BOLD_ITALIC, ITALIC, REGULAR;
  }
}
