package ru.vorazen.main;

import java.awt.Graphics2D;

import ru.vorazen.entity.Block;
import ru.vorazen.entity.Player;

public class World {
    Block blocks[] = new Block[50];
    Player player;

    public int xOTS = 16; // x size of tile
    public int yOTS = 16; // y size of tile
    public final int screenColNum = 32; // width of screen in tiles
    public final int screenRowNum = 18; // height of screen in tiles
    public int screenWidthDef = xOTS * screenColNum;
    public int screenHeightDef = yOTS * screenRowNum;
    private int Xsd = 2;
    private int Ysd = 2;
    public double Xscale = Xsd; // Xscale for OriginalTileSizee
    public double Yscale = Ysd; // Yscale for OriginalTileSiz
    // SCREEN SETTINGS

    public int screenWidth = (int) (screenWidthDef * Xscale); // screen width
    public int screenHeight = (int) (screenHeightDef * Yscale); // screen height
    public int ScreenX = 0;
    public int ScreenY = 0;
    private int ScreenXvel = 0;
    private int ScreenYvel = 0;
    private double xBord = xOTS * 3;
    private double yBord = xOTS * 1;
    public CollisionChecker cc = new CollisionChecker(this);

    public World(GamePanel gp) {
        blocks[4] = new Block("/blocks/wheel.png", "door");
        blocks[1] = new Block("/blocks/door.png", "door");
        blocks[2] = new Block("/blocks/door.png", "door");
        blocks[3] = new Block("/blocks/door.png", "door");
        blocks[0] = new Block("/blocks/door.png", "door");
        blocks[1].worldX = -xOTS;
        blocks[2].worldX = xOTS;
        blocks[3].worldY = yOTS;
        blocks[0].worldY = -yOTS;
        blocks[4].angleSpeed = 3.14 / 100;
        for (int i = 5; i < blocks.length; i++) {
            blocks[i] = new Block("/blocks/wheel.png", "door");
            blocks[i].worldX = xOTS*i;
            blocks[i].worldY = 11 * yOTS;
        }
        player = new Player("/player/crown.png", "pl");
        player.worldX = screenWidthDef / 2;
        player.worldY = screenHeightDef / 2;
    }

    public void update(GamePanel gp) {
        updateScreen();
        for (Block block : blocks) {
            if (block != null)
                block.update(gp);
        }
        byte y = 0;
        byte x = 0;
        if (gp.IH.downPressed)
            y++;
        if (gp.IH.upPressed)
            y--;
        if (gp.IH.leftPressed)
            x--;
        if (gp.IH.rightPressed)
            x++;

        player.intSpeedX = x * 2;
        player.intSpeedY = y * 2;
        player.accelY = 0.1;
        player.update(gp);

    }

    private void updateScreen() {
        int defX = screenWidthDef * Xsd;
        int defY = screenHeightDef * Ysd;
        int newX = (int) Main.window.getSize().getWidth() - 14;
        int newY = (int) Main.window.getSize().getHeight() - 37;
        // System.err.println(defX+" "+defY);
        // System.err.println(newX+" "+newY);
        // System.out.println(newX+" "+defX+" "+1.0*newX+" "+1.0*newX/defX);
        Xscale = Xsd * (1.0 * newX / defX);
        Yscale = Ysd * (1.0 * newY / defY);
        screenWidth = newX;
        screenHeight = newY;
        int scX = ScreenX + screenWidthDef / 2;
        int scY = ScreenY + screenHeightDef / 2;
        if (Math.abs(player.worldX - scX) > 1.0 * xBord / 2) {
            // ScreenXvel += tension * (player.worldX - scX-1.0 * xBord / 2);
            ScreenXvel = (int) (player.speedX + player.intSpeedX);
        } else {
            ScreenXvel = 0;
        }
        if (Math.abs(player.worldY - scY) > 1.0 * yBord / 2) {
            ScreenYvel = (int) (player.speedY + player.intSpeedY);
        } else {
            ScreenYvel = 0;
        }
        ScreenX += ScreenXvel;
        ScreenY += ScreenYvel;
    }

    public void draw(Graphics2D g2, GamePanel gp) {

        for (Block block : blocks) {
            if (block != null) {
                boolean visible = !(block.collisionHeight + block.worldY < ScreenY
                        || block.worldY > ScreenY + screenHeightDef
                        || block.collisionWidth + block.worldX < ScreenX || block.worldX > ScreenX + screenWidthDef);
                if (visible) {
                    block.draw(g2, this);
                    // System.err.println(block.name);
                }

            }

        }
        player.draw(g2, this);
    }
}
