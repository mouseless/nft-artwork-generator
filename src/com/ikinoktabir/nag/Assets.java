package com.ikinoktabir.nag;

import java.io.File;

public class Assets {
    private final Category[] categories;
    private Combination[] combinations;

    public Assets(String folder) {
        var folders = new File(folder).listFiles();

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
}
