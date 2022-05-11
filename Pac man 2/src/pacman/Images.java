package pacman;

import java.awt.Image;
import javax.swing.ImageIcon;



public class Images extends ImageIcon {
  public Image heart,ghost;
  public Image up, down, left, right;
	 public void loadImages() {
	    	
	  down = new ImageIcon(Image.class.getResource("/down.gif")).getImage();
	   up= new ImageIcon(Image.class.getResource("/up.gif")).getImage();
	   left = new ImageIcon(Image.class.getResource("/left.gif")).getImage();
	   right = new ImageIcon(Image.class.getResource("/right.gif")).getImage();
	   ghost = new ImageIcon(Image.class.getResource("C:/resim/ghost.gif")).getImage();
	     heart = new ImageIcon(Image.class.getResource("/heart.png")).getImage();


}
}