package com.ikinoktabir.nag;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.Date;

public class Painter extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int PIXELS_H = 890;
    public static final int PIXELS_W = 640;

    public Painter() {
        super();

        setSize(PIXELS_W, PIXELS_H);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        draw(g);

        save("image");
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, PIXELS_W, PIXELS_H);

        g.drawImage(load("sunglasses/image"), 0, 0, this);
        g.drawImage(load("sunglasses/sunglasses-glasses-png-image-55266d1df8084d83c93538553e149524"), 0, 0, this);

        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 100, 100);
    }

    public BufferedImage load(String asset) {
        try {
            return ImageIO.read(new File("assets/" + asset + ".png"));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void save(String output) {
        var bImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        var cg = bImg.createGraphics();

        draw(cg);

        EventQueue.invokeLater(() -> {
            try {
                var fileName = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
                ImageIO.write(bImg, "png", new File("outputs/" + output + "_" + fileName + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}