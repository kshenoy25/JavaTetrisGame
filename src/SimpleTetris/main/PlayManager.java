package main;

import tetrisMino.*;

import java.awt.*;
import java.util.ArrayList;
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
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

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

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // set the starting mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
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
        // check if the currentMino is active

        if (currentMino.active == false){
            // if the mino is not active, put it into the staticBlocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            currentMino.deactivating = false;

            // replace the currentMino with the nextMino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // when a mino becomes inactive, check if line(s) can be deleted
            checkDelete();
        }
        else {
            currentMino.update();
        }
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while(x < right_x && y < bottom_y){

            for (int i = 0; i < staticBlocks.size(); i++){
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    // increase the count if there is a static block
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if (x == right_x){

                // if the blockCount hits 12, that means the current y line is all filled with blocks
                // so we can delete them
                if (blockCount == 12){

                    for (int i = staticBlocks.size() - 1; i > -1; i--){
                        // remove all the blocks in the current y line
                        if (staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }

                    // a line has been deleted so no need to slide down blocks that are above it
                    for (int i = 0; i < staticBlocks.size(); i++){
                        // if a block is above the current y, move it down by the block size
                        if (staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
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
        // draw nextMino
        nextMino.draw(g2);

        // draw static blocks
        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        // draw pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(KeyHandler.pausePressed){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    }
}
