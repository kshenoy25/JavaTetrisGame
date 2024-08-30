package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

        public static final int WIDTH = 1200;
        public static final int HEIGHT = 720;
        final int FPS = 60;
        Thread gameThread;
        PlayManager pm;

        public GamePanel(){
            // panel settings
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(Color.BLACK);
            this.setLayout(null);

            pm = new PlayManager();

        }
        public void launchGame(){
            gameThread = new Thread(this);
            // starting a thread will call the run() method
            gameThread.start();
        }

    @Override
    public void run() {
            // game loop | update and draw
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
        }
        if (delta >= 1){
            update();
            repaint();
            delta--;
        }
    }

    private void update(){
        pm.update();
    }

    public void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            pm.draw(g2);
    }
}