import javax.swing.*;

/**
 * Launcher for the 3D renderer application.
 */
public class Main {

  private static final JFrame FRAME = new JFrame();

  public static void main(String[] args) throws Throwable {
    setupMainFrame();
  }

  /**
   * Configures the main JFrame properties.
   */
  private static void setupMainFrame() {
    FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    FRAME.setSize(420, 360);
    FRAME.setVisible(true);
  }

}
