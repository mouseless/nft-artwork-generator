package com.hipsteranimals.nag;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class CardPrinter extends Printer {
    private static final long serialVersionUID = 1L;

    private final Timer repaintTimer;
    private int index;

    public CardPrinter(PrintOptions options, Collection collection) {
        super(options, collection);

        repaintTimer = new Timer(60 * 1000 / options.rpm, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index++;

                if (index >= collection.collectables().length) {
                    repaintTimer.stop();
                } else {
                    repaint();
                }
            }
        });

        repaintTimer.start();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawCollectable(collection.collectable(index), g, 0, 0);
    }

    @Override
    protected String fileName() {
        return "cryptodog_" + index + ".png";
    }
}