package com.ikinoktabir.nag;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Application extends JFrame {
    private static final long serialVersionUID = 1L;

    public Application() {
        initUI(Painter.PIXELS_W, Painter.PIXELS_H);
    }

    private void initUI(int sizeX, int sizeY) {
        setSize(sizeX, sizeY + 20);
        setTitle("NAG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new Painter());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Application().setVisible(true));
    }
}