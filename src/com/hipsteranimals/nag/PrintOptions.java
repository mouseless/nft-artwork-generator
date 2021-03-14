package com.hipsteranimals.nag;

import java.io.File;

public class PrintOptions {
    public final File folder;
    public final int width;
    public final int height;
    public final int rpm;

    public PrintOptions(File folder, int width, int height) {
        this(folder, width, height, -1);
    }

    public PrintOptions(File folder, int width, int height, int rpm) {
        this.folder = folder;
        this.width = width;
        this.height = height;
        this.rpm = rpm;
    }
}
