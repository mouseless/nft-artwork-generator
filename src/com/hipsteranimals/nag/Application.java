package com.hipsteranimals.nag;

import java.awt.EventQueue;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

public class Application extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Application().setVisible(true));
    }

    private static final long serialVersionUID = 1L;

    public static final int PIXELS_W = 490;
    public static final int PIXELS_H = 740;
    public static final int F_I_PIXELS_W = 1200;
    public static final int F_I_PIXELS_H = 800;
    public static final int RENDER_PER_MINUTE = 240;

    public Application() {
        initUI();
    }

    private void initUI() {
        setTitle("NAG");
        // setSize(PIXELS_W, PIXELS_H + 20);
        setSize(F_I_PIXELS_W, F_I_PIXELS_H + 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var folder = new File("outputs/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        if (!folder.exists()) {
            folder.mkdirs();
        }

        var dogs = new Collection("assets/HipsterDogs");
        dogs.build();

        // add(new CardPrinter(folder, PIXELS_W, PIXELS_H, RENDER_PER_MINUTE, dogs));
        add(new FeaturedImagePrinter(new PrintOptions(folder, F_I_PIXELS_W, F_I_PIXELS_H), dogs));
    }
}