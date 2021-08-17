package de.gaudinicki.panzerhq.window.menu;

import de.gaudinicki.panzerhq.tank.ITank;
import de.gaudinicki.panzerhq.window.GamePanel;
import de.gaudinicki.panzerhq.window.TankPreviewPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

public class ChooseTankColorDialog extends JDialog {

    private final GamePanel gamePanel;
    private final ITank playersTank;
    
    private final JPanel dialogPanel;
    
    private Color oldTurretColor;
    private Color oldCannonColor;
    private Color tempTurretColor;
    private Color tempCannonColor;
    
    public ChooseTankColorDialog(Frame frame, GamePanel gamePanel) {
        super(frame);

        this.gamePanel = gamePanel;
        this.playersTank = this.gamePanel.getPlayersTank();

        this.dialogPanel = new JPanel();
        this.dialogPanel.add(createPreviewPanel());
        this.dialogPanel.add(createButtonsPanel());

        this.oldTurretColor = playersTank.getTurretColor();
        this.tempTurretColor = oldTurretColor;
        this.oldCannonColor = playersTank.getCannonColor();
        this.tempCannonColor = oldCannonColor;

        this.add(dialogPanel);
        this.setTitle("Choose Your Tank's Colors");
        this.setModal(true);
        this.setPreferredSize(new Dimension(350,350));
    }    
    
    private JPanel createPreviewPanel() {
        JLabel titleLabel = new JLabel("Tank Preview");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        JPanel previewPanel = new JPanel();        
        previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));
        previewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        previewPanel.add(titleLabel);
        previewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        previewPanel.add(new TankPreviewPanel(playersTank));
        
        return previewPanel;
    }
    
    private JPanel createButtonsPanel() {
    	JPanel colorButtonsPanel = new JPanel();
        colorButtonsPanel.add(getChooseTurretColorButton());
        colorButtonsPanel.add(getChooseCannonColorButton());
        
        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.add(getCancelButton());
        actionButtonsPanel.add(getApplyButton());
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(colorButtonsPanel);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonsPanel.add(new JSeparator());
        buttonsPanel.add(actionButtonsPanel);
        
        return buttonsPanel;
    }
    
    private JButton getChooseTurretColorButton() {
        JButton chooseTurretColorButton = new JButton("Choose Turret Color...");

        chooseTurretColorButton.addActionListener(event -> {
            Color newTurretColor = JColorChooser.showDialog(null, "Choose Turret Color", tempTurretColor);
            if (newTurretColor != null) {
                tempTurretColor = newTurretColor;
                playersTank.setTurretColor(newTurretColor);
                dialogPanel.repaint();
            }
        });

        return chooseTurretColorButton;
    }

    private JButton getChooseCannonColorButton() {
        JButton chooseCannonColorButton = new JButton("Choose Cannon Color...");

        chooseCannonColorButton.addActionListener(event -> {
            Color newCannonColor = JColorChooser.showDialog(null, "Choose Cannon Color", tempCannonColor);
            if (newCannonColor != null) {
                tempCannonColor = newCannonColor;
                playersTank.setCannonColor(newCannonColor);
                dialogPanel.repaint();
            }
        });

        return chooseCannonColorButton;
    }

    private JButton getCancelButton() {
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(event -> {
            playersTank.setTurretColor(oldTurretColor);
            playersTank.setCannonColor(oldCannonColor);

            tempTurretColor = oldTurretColor;
            tempCannonColor = oldCannonColor;

            gamePanel.continueGame();
            setVisible(false);
        });

        return cancelButton;
    }

    private JButton getApplyButton() {
        JButton applyButton = new JButton("Apply");

        applyButton.addActionListener(event -> {
            oldTurretColor = playersTank.getTurretColor();
            oldCannonColor = playersTank.getCannonColor();

            gamePanel.repaint();
            gamePanel.continueGame();
            setVisible(false);
        });

        return applyButton;
    }
}

