import java.awt.*;

public class PlayManager {
    // draws the play area
    // manages tetrominoes
    // handles gameplay actions (deleting lines, adding scores, etc.)

    // main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;


    public PlayManager(){
        // main play area frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
    }

    public void update(){

    }
    public void draw(Graphics2D g2){
        // draw main play area
        g2.setColor(Color.white);
        g2.setStroke(Color.white);
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

    }
}
