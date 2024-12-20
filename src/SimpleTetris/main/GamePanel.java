package main;

import javax.swing.*;
import java.awt.*; // abstract window toolkit
import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements Runnable{

        public static final int WIDTH = 1200;
        public static final int HEIGHT = 720;
        final int FPS = 60;
        Thread gameThread;
        PlayManager pm;
        public static Sound music = new Sound();
        public static Sound se = new Sound();

        public GamePanel(){
            // panel settings
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(Color.BLACK);
            this.setLayout(null);

            // implement KeyListener
            this.addKeyListener(new KeyHandler());
            this.setFocusable(true);

            pm = new PlayManager();

        }
        public void launchGame(){
            gameThread = new Thread(this);
            // starting a thread will call the run() method
            gameThread.start();

            music.play(0, true);
            music.loop();

        }

    @Override
    public void run() {
        // game loop | update and draw
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update(){
            if (KeyHandler.pausePressed == false && pm.gameOver == false){
                pm.update();
            }
        }

    public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            pm.draw(g2);
    }
}
