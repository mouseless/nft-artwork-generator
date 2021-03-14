package com.hipsteranimals.nag;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Asset {
    private final Category category;
    private final File file;
    private Image image;

    public Asset(Category category, File file) {
        this.category = category;
        this.file = file;
    }

    public Category category() {
        return category;
    }

    public Image image() {
        if(image == null){
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                throw new Error(e);
            }
        }

        return image;
    }
}
