package com.ikinoktabir.nag;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Application extends JFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Application().setVisible(true));
    }

    private static final long serialVersionUID = 1L;

    public static final int PIXELS_W = 490;
    public static final int PIXELS_H = 740;
    public static final int RENDER_PER_MINUTE = 120;

    public Application() {
        initUI();
    }

    private void initUI() {
        setSize(PIXELS_W, PIXELS_H + 20);
        setTitle("NAG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new Painter(PIXELS_W, PIXELS_H, RENDER_PER_MINUTE, new Assets("assets")));
    }
}