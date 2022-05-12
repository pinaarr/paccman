package pacman;

import javax.swing.JFrame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;

public class Pacman extends JFrame{

public Pacman() {
add(new Model());
}


public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
Scanner scanner= new Scanner(System.in);

File file =new File("C:\\Users\\buket\\Downloads\\Madonna - Material girl (instrumental).wav");
AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
Clip clip= AudioSystem.getClip();
clip.open(audioStream);

clip.start();



Pacman pac = new Pacman();
pac.setVisible(true);
pac.setTitle("Pacman");
pac.setSize(380,420);
pac.setDefaultCloseOperation(EXIT_ON_CLOSE);
pac.setLocationRelativeTo(null);

String response = scanner.next();
}

}



        

    
		
		
	





