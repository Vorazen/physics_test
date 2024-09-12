package ru.vorazen.entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.vorazen.main.GamePanel;
import ru.vorazen.main.World;

public class Block extends SuperEntity {
    public Block(String path, String name) {
        this.name = name;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            // TODO: handle exception
        }
    }


    @Override
    public void draw(Graphics2D g2, World world) {
        /*
         * double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
         * int w = image.getWidth(), h = image.getHeight();
         * int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h *
         * cos + w * sin);
         * GraphicsConfiguration gc = getDefaultConfiguration();
         * BufferedImage result = gc.createCompatibleImage(neww, newh,
         * Transparency.TRANSLUCENT);
         * Graphics2D g = result.createGraphics();
         * g.translate((neww - w) / 2, (newh - h) / 2);
         * g.rotate(angle, w / 2, h / 2);
         * g.drawRenderedImage(image, null);
         * g.scale(world.Xscale, world.Yscale);
         * g2.drawImage(result, (int) ((worldX - world.ScreenX) * world.Xscale),
         * (int) ((worldY - world.ScreenY) * world.Yscale), (int) (this.Xsize *
         * world.Xscale),
         * (int) (this.Ysize * world.Xscale), null);
         */
        double radians = angle;
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        //System.err.println(newWidth+" "+newHeight+" "+angle+" "+image.getWidth()+" "+image.getHeight());
        // Создаем новое изображение с рассчитанными размерами
        BufferedImage rotatedImg = new BufferedImage(newWidth, newHeight, image.getType());

        // Создаем графический контекст для нового изображения
        Graphics2D g2d = rotatedImg.createGraphics();

        // Устанавливаем трансформацию
        AffineTransform at = new AffineTransform();
        // Перемещаем центр изображения в центр нового изображения
        at.translate((newWidth - image.getWidth()) / 2, (newHeight - image.getHeight()) / 2);
        // Поворачиваем изображение
        at.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);

        // Применяем трансформацию
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        g2.drawImage(rotatedImg, (int) ((worldX - world.ScreenX) * world.Xscale)-(int) ((this.XSpriteSize * world.Xscale*newWidth/image.getWidth())/2)+(int) ((this.XSpriteSize * world.Xscale)/2),
                (int) ((worldY - world.ScreenY) * world.Yscale)-(int) ((this.YSpriteSize * world.Yscale*newHeight/image.getHeight())/2)+(int) ((this.YSpriteSize * world.Yscale)/2), (int) (this.XSpriteSize * world.Xscale*newWidth/image.getWidth()),
                (int) (this.YSpriteSize * world.Yscale*newHeight/image.getHeight()), null);
             // g2.draw(new Rectangle((int) ((worldX - world.ScreenX) * world.Xscale),(int) ((worldY - world.ScreenY) * world.Yscale),1,(int) (this.YSpriteSize * world.Yscale*newHeight/image.getHeight())));
              //  g2.draw(new Rectangle((int) ((this.XSpriteSize * world.Xscale*newWidth/image.getWidth())/2),(int) ((this.YSpriteSize * world.Yscale*newHeight/image.getHeight())/2),1,1));
    }

    @Override
    public void update(GamePanel gp) {
        boolean canMove = true;
        if (canMove) {
            worldX += speedX + intSpeedX;
            worldY += speedY + intSpeedY;
            speedX += accelX + intAccelX;
            speedY += accelY + intAccelY;
        }
        boolean canRotate = true;
        if (canRotate) {
            angle += angleSpeed;
            angleSpeed += angleAccel;
            intAngleSpeed += intAngleAccel;
        }
    }

}
