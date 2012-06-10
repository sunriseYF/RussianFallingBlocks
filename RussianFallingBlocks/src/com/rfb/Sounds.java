package com.rfb;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Sounds {
	
	Sequencer sequencer;
	int musicInd;
	static final int NUM_TRACKS = 3;
	
	public Sounds() {
		try {
			this.sequencer = MidiSystem.getSequencer();
			this.sequencer.open();
		} catch(Exception e) {
			this.sequencer = null;
		}
	}
	
	private static Sequence getSequence(int ind) {
		try {
			String midiFile;
			if(ind == 0) midiFile = "tetrisb.mid";
			else if(ind == 1) midiFile = "tetrisc.mid"; 
			else midiFile = "tetrisa.mid";
			return MidiSystem.getSequence(Sounds.class.getResource(midiFile));
		} catch(Exception ioe) {
			return null;
		}
	}
	
	/**
	 * Selects and plays the new song
	 * @param musicId
	 */
	public void playMusic(int musicId) {
		if(musicId >= 0) switchMusic(musicId);
		else if(sequencer.getSequence() == null) switchMusic();
		if(sequencer != null && !sequencer.isRunning()) {
			sequencer.start();
			sequencer.setLoopCount(100);
		}
	}
	
	/**
	 * Plays the currently selected song
	 */
	public void playMusic() {
		playMusic(-1);
	}
	
	public void stopMusic() {
		if(sequencer != null && sequencer.isRunning()) sequencer.stop();
	}
	
	public void toggleMusic() {
		if(sequencer != null && sequencer.isRunning()) sequencer.stop();
		else if(sequencer != null) sequencer.start();
	}
	
	public void switchMusic() {
		int newInd = musicInd;
		while(newInd == musicInd) {
			double rand = Math.random();
			newInd = (int) Math.floor(rand * NUM_TRACKS) + 1;
		}
		this.musicInd = newInd;
		switchMusic(musicInd);
	}
	
	private void switchMusic(int ind) {
		Sequence sequence = getSequence(ind);		
		if(sequencer != null && sequence != null) {
			try {
				sequencer.setSequence(sequence);
			} catch(Exception e) {
				sequencer = null;
			}
		}
	}
}
