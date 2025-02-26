package renderer;

import javax.swing.*;
import java.awt.*;

public class Viewer {

	public static void main(String[] args) {
		// Setup window
		JFrame frame = new JFrame();
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		
		JSlider horizontalSlider = new JSlider(0,360, 180);
		pane.add(horizontalSlider, BorderLayout.SOUTH);
		
		JSlider vertSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
		pane.add(vertSlider, BorderLayout.EAST);
		
		JPanel renderPanel = new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		
		pane.add(renderPanel, BorderLayout.CENTER);
		
		frame.setSize(600, 600);
		frame.setVisible(true);
	}
}
