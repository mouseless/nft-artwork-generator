package com.hipsteranimals.nag;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static com.hipsteranimals.nag.Preferences.*;

public class FeaturedImagePrinter extends Printer {
    private static final long serialVersionUID = 1L;

    public FeaturedImagePrinter(PrintOptions options, Collection collection) {
        super(options, collection);
    }

    @Override
    public void paint(Graphics g) {
        if (!file().exists()) {
            super.paint(g);
        }
        
        if (started()) {
            finish();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int tThumbWidth = this.width() / F_I_MAX_COL;
        int tThumbHeight = this.height() / F_I_MAX_ROW;

        double scaleRate = ((double) this.width()) / (collection.width() * F_I_MAX_COL);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.width(), this.height());

        var collectables = collection.collectables(true);

        for (int i = 0, row = 0, col = 0; row < F_I_MAX_ROW; i++, col++) {
            var collectable = collectables[i % collectables.length];

            System.out.printf("drawing @ (%d/%d, %d/%d)", col, F_I_MAX_COL, row, F_I_MAX_ROW);
            System.out.println();
            System.out.printf("drawing @ ( width %d, heihgt %d, scale rate %f)", tThumbWidth, tThumbHeight, scaleRate);

            drawCollectable(collectable, g, col * tThumbWidth, row * tThumbHeight, scaleRate);

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(2));
            g.drawRect(col * tThumbWidth, row * tThumbHeight, tThumbWidth, tThumbHeight);

            if (col >= F_I_MAX_COL - 1) {
                row++;
                col = -1;
            }
        }
    }

    @Override
    protected int width() {
        return F_I_PIXELS_W;
    }

    @Override
    protected int height() {
        return F_I_PIXELS_H;
    }

    @Override
    protected String fileName() {
        return collection.name() + "_feature_image.png";
    }
}