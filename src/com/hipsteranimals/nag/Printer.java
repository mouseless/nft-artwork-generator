package com.hipsteranimals.nag;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.EventQueue;

public abstract class Printer extends JPanel {
    private static final long serialVersionUID = 1L;

    protected final PrintOptions options;
    protected final Collection collection;

    protected Printer(PrintOptions options, Collection collection) {
        this.options = options;
        this.collection = collection;

        setSize(options.width, options.height);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        var bImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        var cg = bImg.createGraphics();

        draw((Graphics2D) cg);

        g.drawImage(bImg, 0, 0, this);

        var filePath = options.folder.getPath() + "/" + fileName();

        EventQueue.invokeLater(() -> {
            try {
                ImageIO.write(bImg, "png", new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    protected abstract void draw(Graphics2D g);

    protected abstract String fileName();

    protected void drawCollectable(Collectable collectable, Graphics2D g, int x, int y) {
        drawCollectable(collectable, g, x, y, 1);
    }

    protected void drawCollectable(Collectable collectable, Graphics2D g, int x, int y, double scaleRate) {
        for (var asset : collectable.assets()) {
            g.drawImage(scale(asset.image(), scaleRate), x, y, this);
        }
    }

    protected Image scale(Image image, double scaleRate) {
        if (scaleRate == 1.0) {
            return image;
        }

        var width = (int) (image.getWidth(this) * scaleRate);
        var height = (int) (image.getHeight(this) * scaleRate);

        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
