package pacman;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Model extends JPanel implements ActionListener {

	private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    public boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_Boyutu = 24;
    private final int N_BLOCKS = 15;
    private final int EKRAN_BOYUTU = N_BLOCKS * BLOCK_Boyutu;
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_HIZ = 4;

    private int N_GHOSTS = 6;
    private int can, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    public Image kalp, hayalet;
    public
    Image üst, alt, sol, sað;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    public int req_dx, req_dy;

    private final short levelData[] = {
    		19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
            17, 16, 16, 16, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 20,
            17, 16, 16, 20,  0,  0,  0,  0,  0,  0,  0, 17, 16, 16, 20,
            17, 16, 16, 20,  0,  0,  0,  0,  0,  0,  0 ,17, 16, 16, 20,
            17, 16, 16, 16, 18, 18, 18, 18, 18, 18, 18, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            17, 16, 24, 24, 16, 16, 16, 16, 16, 16, 16, 24, 24,  16, 20,
            17, 20,  0,  0, 17, 16, 16, 16, 16, 16, 20,  0,  0, 17, 20,
            17, 20,  0,  0 ,17, 16, 16, 16, 16, 16, 20,  0,  0, 17, 20,
            17, 20,  0,  0, 17, 16, 16, 16, 16, 16, 20, 0,  0, 17, 20,
            17, 20,  0,  0, 17, 16, 16, 16, 16, 16, 20,  0,  0 , 17, 20,
            17, 16, 18, 18, 16, 24, 24, 24,24, 24, 16, 18, 18, 16, 20,
            17, 16, 16, 16, 20,  0,  0,  0, 0,  0, 17, 16, 16, 16, 20,
            25, 24, 24, 24, 24, 26, 26, 26, 26, 26, 24, 24, 24, 24, 28
    };

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
    private short[] screenData;
    public Timer timer;

    public Model() {
    	Images all_images=new Images();
    	// system.out.println("here 2");
    	all_images.loadImages();
    	
    	alt= all_images.alt;
    	üst= all_images.üst;
    	sol= all_images.sol;
    	sað=all_images.sað;
    	hayalet=all_images.hayalet;
    	kalp=all_images.kalp;
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
       
    }
    
       private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        
        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {
 
    	String start = "Baþlamak Ýçin Boþluk Tuþuna Basýn";
        g2d.setColor(Color.black);
        g2d.drawString(start, (EKRAN_BOYUTU)/6, 150);
    }

    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(0,0,0));
        String s = "Score: " + score;
        g.drawString(s, EKRAN_BOYUTU/ 2 + 96,EKRAN_BOYUTU + 16);

        for (int i = 0; i < can; i++) {
            g.drawImage(kalp, i * 28 + 8, EKRAN_BOYUTU + 1, this);
        }
    }

    private void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

    	can--;

        if (can == 0) {
            inGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_Boyutu == 0 && ghost_y[i] % BLOCK_Boyutu == 0) {
                pos = ghost_x[i] / BLOCK_Boyutu + N_BLOCKS * (int) (ghost_y[i] / BLOCK_Boyutu);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(hayalet, x, y, this);
        }

    private void movePacman() {

        int pos;
        short ch;

        if (pacman_x % BLOCK_Boyutu == 0 && pacman_y % BLOCK_Boyutu == 0) {
            pos = pacman_x / BLOCK_Boyutu+ N_BLOCKS * (int) (pacman_y / BLOCK_Boyutu);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        } 
        pacman_x = pacman_x + PACMAN_HIZ * pacmand_x;
        pacman_y = pacman_y + PACMAN_HIZ * pacmand_y;
   if (score==190) {
	   pacmand_x=0;
	   pacmand_y=0;
   }
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
        	g2d.drawImage(sol, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(sað, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(üst, pacman_x + 1, pacman_y + 1, this);
        } else {
        	g2d.drawImage(alt, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;
 
        
        for (y = 0; y < EKRAN_BOYUTU; y += BLOCK_Boyutu) {
            for (x = 0; x < EKRAN_BOYUTU; x += BLOCK_Boyutu) {

                g2d.setColor(new Color(255,102,102));
                g2d.setStroke(new BasicStroke(5));
                
                if ((levelData[i] == 0)) { 
                	g2d.fillRect(x, y, BLOCK_Boyutu, BLOCK_Boyutu);
                 }

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_Boyutu - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_Boyutu - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_Boyutu - 1, y, x + BLOCK_Boyutu - 1,
                            y + BLOCK_Boyutu - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_Boyutu - 1, x + BLOCK_Boyutu - 1,
                            y + BLOCK_Boyutu - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }

                i++;
            }
        }
    }

    private void initGame() {

    	can = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

    	int dx = 1;
        int random;

        for (int i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_Boyutu; //start position
            ghost_x[i] = 4 * BLOCK_Boyutu;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_Boyutu;  //start position
        pacman_y = 11 * BLOCK_Boyutu;
        pacmand_x = 0;	//reset direction move
        pacmand_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }

 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.pink);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

 class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

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

	
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
		
	}
          
                
            
        
        


	
   
   
		
	
