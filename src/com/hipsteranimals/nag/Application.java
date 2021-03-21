package com.hipsteranimals.nag;

import java.awt.EventQueue;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Application extends JFrame implements Printer.Finished {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Application().setVisible(true));
    }

    private static final long serialVersionUID = 1L;

    //public static final int PIXELS_W = 1240;
    //public static final int PIXELS_H = 1754;
    public static final int PIXELS_W = 490;
    public static final int PIXELS_H = 740;
    public static final int F_I_PIXELS_W = 6000;
    public static final int F_I_PIXELS_H = 4000;
    public static final int RENDER_PER_MINUTE = 240;
    public static final double SCALE_RATE = 0.1;

    private final List<Printer> printers;
    private int printerIndex;

    public Application() {
        setTitle("N-A-G");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        var output = new File("outputs");
        if (!output.exists()) {
            output.mkdirs();
        }

        var folders = new File("assets").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("Hipster");
            }
        });

        printers = new ArrayList<Printer>();
        printerIndex = 0;

        for (var folder : folders) {
            var collection = new Collection(folder);
            collection.build();

            var cardPrinter = new CardPrinter(new PrintOptions(output, RENDER_PER_MINUTE), collection);
            cardPrinter.addFinishedListener(this);
            printers.add(cardPrinter);

            var featuredImagePrinter = new FeaturedImagePrinter(new PrintOptions(output), collection);
            featuredImagePrinter.addFinishedListener(this);
            printers.add(featuredImagePrinter);
        }

        printNext();
    }

    public void printNext() {
        var printer = printers.get(printerIndex);

        setSize(printer.width(), printer.height() + 20);
        add(printer);

        printer.start();
    }

    @Override
    public void onFinish() {
        remove(printers.get(printerIndex));

        printerIndex++;

        if(printerIndex >= printers.size()) {
            return;
        }

        printNext();
    }
}