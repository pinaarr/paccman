package pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
 int req_dx,req_dy;
        int key = e.getKeyCode();

        if (inGame) {
            if (key == KeyEvent.VK_LEFT) {
                req_dx = -1;
                req_dy = 0;
            } else if (key == KeyEvent.VK_RIGHT) {
                req_dx = 1;
                req_dy = 0;
            } else if (key == KeyEvent.VK_UP) {
                req_dx = 0;
                req_dy = -1;
            } else if (key == KeyEvent.VK_DOWN) {
                req_dx = 0;
                req_dy = 1;
            } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                inGame = false;
            } 
        } else {
            if (key == KeyEvent.VK_SPACE) {
                inGame = true;
                initGame();
            }
        }
    }
}

}
