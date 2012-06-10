package com.rfb;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class RussianFallingBlocks extends JFrame {
	
	private static final long serialVersionUID = 2722898184561488980L;
	
	StatusBar statusBar;
	Game game;

	public RussianFallingBlocks() {
		game = new Game(this);
		add(game);
		
		statusBar = new StatusBar(game);		
		add(statusBar, BorderLayout.SOUTH);
		
		setJMenuBar(getMenu());
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("rfb.png"));
			setIconImage(image);
		} catch (IOException e) {
			System.out.println("Unable to set the icon: "+e.getMessage());
		}
		
		setSize(350, 700);
		setTitle("Russian Falling Blocks");
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public StatusBar getStatusBar() {
		return statusBar;
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public static void main(String[] args) {
		RussianFallingBlocks program = new RussianFallingBlocks();
		program.setLocationRelativeTo(null);
		program.setVisible(true);
	}
	
	public JMenuBar getMenu() {
		JMenuBar menubar = new JMenuBar();

        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        JMenuItem startNew = new JMenuItem("Begin New Game (b)");
        startNew.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		getGame().clearBoard();
        		getGame().start();
        	}
        });
        menu.add(startNew);
        
        JMenuItem pause = new JMenuItem("Play/Pause (p)");
        pause.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		getGame().playPause(e.getID());
        	}
        });
        menu.add(pause);
        
        JMenuItem toggleSounds = new JMenuItem("Toggle Sounds (s)");
        toggleSounds.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e) {
        		getGame().getSounds().toggleMusic();
        	}
        });
        menu.add(toggleSounds);
        
		JMenuItem help = new JMenuItem("Help (h)");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelp(game);
			}
		});
		menu.add(help);
		
		JMenuItem quit = new JMenuItem("Quit (q)");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().exit();
			}
		});
		menu.add(quit);

        menubar.add(menu);
        return menubar;
	}
	
	public static void showHelp(Game g) {
		if(!g.isPaused()) g.playPause('n');
		StringBuffer helpText = new StringBuffer();
		helpText.append("Left/Right: Move Piece Left or Right").append("\n");
		helpText.append("Up/Down: Rotate Piece Left or Right").append("\n");
		helpText.append("D: Drop piece one line (ahead of timer)").append("\n");
		helpText.append("Space: Drop piece to the bottom").append("\n");
		helpText.append("P: Pause or unpause").append("\n");
		helpText.append("Q: Quit").append("\n");
		JOptionPane.showMessageDialog(g.getParent(), helpText.toString());

	}
}