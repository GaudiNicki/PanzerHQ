package de.gaudinicki.panzerhq.window.menu;

import de.gaudinicki.panzerhq.window.GamePanel;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

    private final GamePanel gamePanel;
    private final ChooseTankColorDialog chooseTankColorDialog;

    public MenuBar(JFrame gameWindow, GamePanel gamePanel) {
        super();

        this.gamePanel = gamePanel;
        this.chooseTankColorDialog = new ChooseTankColorDialog(gameWindow, this.gamePanel);

        initializeMenu();
    }

    private void initializeMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");
        JMenu prefMenu = new JMenu("Preferences");

        this.add(fileMenu);
        this.add(gameMenu);
        this.add(prefMenu);

        addFileMenuItems(fileMenu);
        addGameMenuItems(gameMenu);
        addPreferencesMenuItems(prefMenu);
    }

    private void addFileMenuItems(JMenu fileMenu){
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(quitItem);
    }

    private void addGameMenuItems(JMenu gameMenu) {
        JMenuItem pauseItem = new JMenuItem("Pause");
        pauseItem.addActionListener(event -> this.gamePanel.pauseGame());
        gameMenu.add(pauseItem);

        JMenuItem continueItem = new JMenuItem("Continue");
        continueItem.addActionListener(event -> this.gamePanel.continueGame());
        gameMenu.add(continueItem);

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(event -> this.gamePanel.restartGame());
        gameMenu.add(restartItem);
    }

    private void addPreferencesMenuItems(JMenu preferencesMenu) {
        JMenuItem changeColorItem = new JMenuItem("Change Tank Colors...");
        changeColorItem.addActionListener(event -> {
            this.gamePanel.pauseGame();
            chooseTankColorDialog.pack();
            chooseTankColorDialog.setLocation(300, 200);
            chooseTankColorDialog.setVisible(true);
        });
        preferencesMenu.add(changeColorItem);

        JMenu chooseBackgroundSubMenu = new JMenu("Choose Background");
        preferencesMenu.add(chooseBackgroundSubMenu);

        JMenuItem mudAreaItem = new JMenuItem("Mud Area");
        mudAreaItem.addActionListener(event -> {
            this.gamePanel.setBackgroundImage("bg_mud.jpg");
            repaint();
        });
        chooseBackgroundSubMenu.add(mudAreaItem);

        JMenuItem snowAreaItem = new JMenuItem("Snow Area");
        snowAreaItem.addActionListener(event -> {
            this.gamePanel.setBackgroundImage("bg_snow.jpg");
            repaint();
        });
        chooseBackgroundSubMenu.add(snowAreaItem);

        JMenuItem desertAreaItem = new JMenuItem("Desert Area");
        desertAreaItem.addActionListener(event -> {
            this.gamePanel.setBackgroundImage("bg_desert.jpg");
            repaint();
        });
        chooseBackgroundSubMenu.add(desertAreaItem);
    }
}
