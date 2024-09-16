package mae.game.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[40];
	FloatControl fc;
	public int volumeScale = 3;
	float volume;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/res/sounds/correct.wav"); //Up/Down
		soundURL[1] = getClass().getResource("/res/sounds/correct.wav"); //Select
		soundURL[2] = getClass().getResource("/res/sounds/correct.wav"); //Correct
		soundURL[3] = getClass().getResource("/res/sounds/wrong.wav"); //Wrong
		soundURL[4] = getClass().getResource("/res/sounds/correct_door.wav"); //Correct + open door
		//Menu song
		soundURL[31] = getClass().getResource("/res/music/Casual-Theme.wav"); //Woodland song
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		} catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void checkVolume() {
		switch(volumeScale) {
		case 0: volume = -90f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f; break;
		case 3: volume = -5f; break;
		case 4: volume = 1f; break;
		case 5: volume = 6f; break;
		}
		fc.setValue(volume);
	}
}
