package com.khosla.utilities;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundClip {

	private Clip clip;
	private AudioInputStream audioStream;

	public SoundClip(URL url) {
		try {
			audioStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (clip.isRunning())
			clip.stop(); // Stop the player if it is still running
		clip.setFramePosition(0); // rewind to the beginning
		clip.start(); // Start playing
	}
}