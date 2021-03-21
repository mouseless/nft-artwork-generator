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
                do {
                    index++;
                } while (file().exists());

                System.out.printf("Printing card number %d on collection %s", index, collection.name());
                System.out.println();

                if (index >= collection.collectables().length) {
                    repaintTimer.stop();
                    finish();
                } else {
                    repaint();
                }
            }
        });
    }

    @Override
    public void start() {
        super.start();

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
        return collection.name() + "_" + index + ".png";
    }

    @Override
    protected int width() {
        return collection.width();
    }

    @Override
    protected int height() {
        return collection.height();
    }
}