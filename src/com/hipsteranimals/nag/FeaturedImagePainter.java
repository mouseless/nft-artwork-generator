package com.hipsteranimals.nag;

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

public class FeaturedImagePainter extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Assets assets;
    private final File folder;
    private int tThumbWidth = 25;
    private int tThumbHeight = 37;

    public FeaturedImagePainter(int width, int height, int rpm, Assets assets) {
        super();

        this.assets = assets;

        assets.build();

        folder = new File("outputs/" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()));

        if (!folder.exists()) {
            // folder.mkdirs();
        }

        setSize(width, height);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        draw(g);

        // save();
    }

    public void draw(Graphics g) {
        var col = 0;
        var row = 0;
        var maxCol = getWidth() / tThumbWidth;
        var maxRow = getHeight() / tThumbHeight;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        var combCount = assets.combinations().length;



        for (var i = 0; i < maxCol*maxRow; i++) {
            System.out.printf("drawing at %d %d", col, row);
            System.out.println();
            var combIndex = i%combCount;
            for (var j = 0; j < assets.combination(combIndex).samples().length; j++) {
                var sample = assets.combination(combIndex).sample(j);
                g.drawImage(sample.image().getScaledInstance(tThumbWidth, tThumbHeight, 1), col * tThumbWidth,
                        row * tThumbHeight, this);
            }

            if (col < maxCol) {
                col++;
            } else if (row < maxRow) {
                row++;
                col = 0;
            } else {
                break;
            }
        }
    }

    public void save() {
        var bImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        var cg = bImg.createGraphics();

        draw(cg);

        var fileName = folder.getPath() + "/cryptodog_Feature_Image.png";

        EventQueue.invokeLater(() -> {
            try {
                ImageIO.write(bImg, "png", new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}