package de.gaudinicki.panzerhq.window;

import de.gaudinicki.panzerhq.window.menu.MenuBar;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame {

	private final GamePanel gamePanel;
	
	public GameWindow() {
		this.gamePanel = new GamePanel();


		this.setTitle("PanzerHQ");
		this.setResizable(false);
		this.setVisible(true);

		this.setJMenuBar(new MenuBar(this, gamePanel));
		this.add(gamePanel);
		pack();

		registerWindowListener();
	}
	
	private void registerWindowListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				gamePanel.pauseGame();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				gamePanel.continueGame();
			}
		});
	}
}
