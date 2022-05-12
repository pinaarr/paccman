package pacman;

import java.awt.Image;
import javax.swing.ImageIcon;



public class Images extends ImageIcon {
  public Image heart,ghost;
  public Image up, down, left, right;
	 public void loadImages() {
	    	
	  down = new ImageIcon("./resim/down.gif").getImage();
	   up= new ImageIcon("./resim/up.gif").getImage();
	   left = new ImageIcon("./resim/left.gif").getImage();
	   right = new ImageIcon("./resim/right.gif").getImage();
	   ghost = new ImageIcon("./resim/hayalet.gif").getImage();
	     heart = new ImageIcon("./resim/kalp.gif").getImage();

 System.out.println("here");
}
}