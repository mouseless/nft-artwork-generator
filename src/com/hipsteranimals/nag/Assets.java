package com.hipsteranimals.nag;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class Assets {
    private final Category[] categories;
    private Combination[] combinations;

    public Assets(String folder) {
        var folders = new File(folder).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.contains(".");
            }
        });

        Arrays.sort(folders, new FileSorter());

        categories = new Category[folders.length];
        for (var i = 0; i < folders.length; i++) {
            categories[i] = new Category(folders[i]);
        }

        for (var i = 0; i < folders.length - 1; i++) {
            categories[i].next(categories[i + 1]);
        }
    }

    public void build() {
        if (categories.length <= 0) {
            return;
        }

        combinations = categories[0].build();
    }

    public Category[] categories() {
        return categories;
    }

    public Combination[] combinations() {
        return combinations;
    }

    public Combination combination(int index) {
        return combinations[index];
    }

    class FileSorter implements Comparator<File> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(File a, File b) {
            return a.getName().compareTo(b.getName());
        }
    }
}
