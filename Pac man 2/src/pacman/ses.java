package pacman;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public  class ses  {
static AudioInputStream audioInputStream ;
static Clip clip ;
  public static void PlayGameMusic() {
	  try {
		  audioInputStream= AudioSystem.getAudioInputStream(new File("Madonna-Material girl(instrumental)(2).wav").getAbsoluteFile());
	 clip=AudioSystem.getClip();
	 clip.open(audioInputStream);
	 clip.loop(Clip.LOOP_CONTINUOUSLY);
	 
	 
	  }
  catch (UnsupportAudioFileException ex) {
	  Logger.getLogger(Model.class.getName()).log(Level.SEVERE,null,ex);
	  

  }
catch (IOException ex){
	  Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null,ex);
  }
  catch(LineUnavailableException ex) {
	  Logger.getLogger(Model.class.getName()).log(Level.SEVERE,null,ex);
  }
}
}

	
   