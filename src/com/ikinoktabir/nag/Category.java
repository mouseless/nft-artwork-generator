package com.ikinoktabir.nag;

import java.io.File;
import java.io.FilenameFilter;

public class Category {
    private final Sample[] samples;
    private Category next;

    public Category(File folder) {
        var files = folder.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });
        
        samples = new Sample[files.length];

        for (var i = 0; i < files.length; i++) {
            samples[i] = new Sample(this, files[i]);
        }
    }

    public Sample[] samples() {
        return samples;
    }

    public void next(Category next) {
        this.next = next;
    }

    public Category next() {
        return next;
    }

    public Combination[] build() {
        Combination[] result;
        if (next == null) {
            result = new Combination[samples.length];

            for (var i = 0; i < samples.length; i++) {
                result[i] = new Combination(samples[i]);
            }
        } else {
            var combinations = next.build();
            result = new Combination[samples.length * combinations.length];

            for (var i = 0; i < samples.length; i++) {
                for (var j = 0; j < combinations.length; j++) {
                    result[i * combinations.length + j] = new Combination(samples[i], combinations[j]);
                }
            }
        }

        return result;
    }
}
