package com.hipsteranimals.nag;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.EventQueue;
import static com.hipsteranimals.nag.Preferences.*;

public abstract class Printer extends JPanel {
    private static final long serialVersionUID = 1L;

    protected final PrintOptions options;
    protected final Collection collection;
    private final List<Finished> finishedListeners;
    private boolean started;

    protected Printer(PrintOptions options, Collection collection) {
        this.options = options;
        this.collection = collection;

        finishedListeners = new ArrayList<Finished>();
        started = false;

        if (imageHeight() > SCREEN_HEIGHT) {
            setSize((int) (imageWidth() * ((double) SCREEN_HEIGHT) / imageHeight()), SCREEN_HEIGHT);
        } else {
            setSize(imageWidth(), imageHeight());
        }
    }

    public void start() {
        started = true;

        repaint();
    }

    protected boolean started() {
        return started;
    }

    protected abstract void draw(Graphics2D g);

    protected abstract int imageWidth();

    protected abstract int imageHeight();

    protected abstract String fileName();

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (!started) {
            return;
        }

        var bImg = new BufferedImage(imageWidth(), imageHeight(), BufferedImage.TYPE_INT_ARGB);
        var cg = bImg.createGraphics();

        draw((Graphics2D) cg);

        var screenImg = scale(bImg, ((double) getHeight()) / imageHeight());
        g.drawImage(screenImg, 0, 0, this);

        EventQueue.invokeLater(() -> {
            try {
                ImageIO.write(bImg, "png", file());
            } catch (IOException e) {
                throw new Error(e);
            }
        });
    }

    protected File file() {
        return new File(filePath());
    }

    protected String filePath() {
        return options.output.getPath() + "/" + fileName();
    }

    protected void drawCollectable(Collectable collectable, Graphics2D g, int x, int y) {
        drawCollectable(collectable, g, x, y, 1);
    }

    protected void drawCollectable(Collectable collectable, Graphics2D g, int x, int y, double scaleRate) {
        for (var asset : collectable.assets()) {
            g.drawImage(scale(asset.image(), scaleRate), x, y, null);
        }
    }

    protected Image scale(Image image, double scaleRate) {
        if (scaleRate == 1.0) {
            return image;
        }

        var width = (int) (image.getWidth(null) * scaleRate);
        var height = (int) (image.getHeight(null) * scaleRate);

        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public void addFinishedListener(Finished finished) {
        finishedListeners.add(finished);
    }

    protected void finish() {
        for (var finished : finishedListeners) {
            finished.onFinish();
        }
    }

    public interface Finished {
        public void onFinish();
    }
}
