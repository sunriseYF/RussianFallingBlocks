package com.rfb;

import javax.swing.JLabel;

public class StatusBar extends JLabel {

	private static final long serialVersionUID = -4571523697718163930L;
	
	private String status;
	private Game game;
	
	public StatusBar(Game game) {
		super();
		this.game = game;
		status = "Welcome!";
		update();
	}
	
	public void updateStatus(String status) {
		this.status = status;
		update();
	}
	
	public void update() {
		setText(status+" Level: "+game.getLevel()+", Score: "+game.getScore());
	}	
}