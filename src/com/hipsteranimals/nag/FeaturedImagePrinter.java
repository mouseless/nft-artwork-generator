package com.hipsteranimals.nag;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class FeaturedImagePrinter extends Printer {
    private static final long serialVersionUID = 1L;

    private int tThumbWidth = (int) (Application.PIXELS_W * Application.SCALE_RATE);
    private int tThumbHeight = (int) (Application.PIXELS_H * Application.SCALE_RATE);

    public FeaturedImagePrinter(PrintOptions options, Collection collection) {
        super(options, collection);
    }

    @Override
    public void draw(Graphics2D g) {
        var maxCol = getWidth() / tThumbWidth + 1;
        var maxRow = getHeight() / tThumbHeight + 1;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        var collectables = collection.collectables(true);

        for (int i = 0, row = 0, col = 0; row < maxRow; i++, col++) {
            var collectable = collectables[i % collectables.length];

            System.out.printf("drawing @ (%d/%d, %d/%d)", col, maxCol, row, maxRow);
            System.out.println();

            drawCollectable(collectable, g, col * tThumbWidth, row * tThumbHeight, Application.SCALE_RATE);

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2));
            g.drawRect(col * tThumbWidth, row * tThumbHeight, tThumbWidth, tThumbHeight);

            if (col >= maxCol - 1) {
                row++;
                col = -1;
            }
        }
    }

    @Override
    protected int width() {
        return Application.F_I_PIXELS_W;
    }

    @Override
    protected int height() {
        return Application.F_I_PIXELS_H;
    }

    @Override
    protected String fileName() {
        return collection.name() + "_feature_image.png";
    }
}