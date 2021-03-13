package com.ikinoktabir.nag;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.Date;

import javax.swing.Timer;

public class Painter extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Timer repaintTimer;
    private final Assets assets;
    private int index;
    private final File folder;

    public Painter(int width, int height, int rpm, Assets assets) {
        super();

        this.assets = assets;
        
        assets.build();

        folder = new File("outputs/" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()));
        folder.mkdir();

        setSize(width, height);

        repaintTimer = new Timer(60 * 1000 / rpm, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index++;

                if (index >= assets.combinations().length) {
                    repaintTimer.stop();
                } else {
                    repaint();
                }
            }
        });

        repaintTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        draw(g);

        save();
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (var sample : assets.combination(index).samples()) {
            g.drawImage(sample.image(), 0, 0, this);
        }
    }

    public void save() {
        var bImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        var cg = bImg.createGraphics();

        draw(cg);

        var fileName = folder.getPath() + "/cryptodog_" + index + ".png";

        EventQueue.invokeLater(() -> {
            try {
                ImageIO.write(bImg, "png", new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}