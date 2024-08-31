package main;

import tetrisMino.*;

import java.awt.*;
import java.util.Random;

public class PlayManager {
    // draws the play area
    // manages tetraminoes
    // handles gameplay actions (deleting lines, adding scores, etc.)

    // main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    // others
    public static int dropInterval = 60; // mino drops in every 60 frames



    public PlayManager(){
        // main play area frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // set the starting mino
        currentMino = pickMino()
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }
    private Mino pickMino () {
        // pick a random mino
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0: mino = new Mino_L1();break;
            case 1: mino = new Mino_L2();break;
            case 2: mino = new Mino_Square();break;
            case 3: mino = new Mino_Bar();break;
            case 4: mino = new Mino_T();break;
            case 5: mino = new Mino_Z1();break;
            case 6: mino = new Mino_Z2();break;
        }
        return mino;
    }

    public void update(){
        currentMino.update();

    }
    public void draw(Graphics2D g2){
        // draw main play area
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        // draw next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Ariel", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        //draw the currentMino
        if (currentMino != null){
            currentMino.draw(g2);

        }
    }
}
