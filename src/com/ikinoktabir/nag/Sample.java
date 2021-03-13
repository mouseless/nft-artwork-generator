package com.ikinoktabir.nag;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sample {
    private final File file;
    private Category category;

    public Sample(Category category, File file) {
        this.category = category;
        this.file = file;
    }

    public Category category() {
        return category;
    }

    public Image image() {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}