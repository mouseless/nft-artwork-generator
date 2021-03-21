package com.hipsteranimals.nag;

import java.io.File;

public class PrintOptions {
    public final File output;
    public final int rpm;

    public PrintOptions(File output) {
        this(output, -1);
    }

    public PrintOptions(File output, int rpm) {
        this.output = output;
        this.rpm = rpm;
    }
}
