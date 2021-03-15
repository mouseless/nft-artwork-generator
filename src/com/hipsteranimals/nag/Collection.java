package com.hipsteranimals.nag;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Collection {
    private final File folder;
    private final Category[] categories;
    private Collectable[] collectables;

    public Collection(File folder) {
        this.folder = folder;

        var folders = folder.listFiles(new FilenameFilter() {
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

        collectables = categories[0].build();
    }

    public String name() {
        return folder.getName();
    }

    public int width() {
        if (!hasAnyCollectable()) {
            return 0;
        }

        return aCollectable().image().getWidth(null);
    }

    public int height() {
        if (!hasAnyCollectable()) {
            return 0;
        }

        return aCollectable().image().getHeight(null);
    }

    public Category[] categories() {
        return categories;
    }

    public Collectable[] collectables() {
        return collectables;
    }

    public Collectable[] collectables(boolean shuffle) {
        if (!shuffle) {
            return collectables;
        }

        var shuffledList = Arrays.asList(collectables);
        Collections.shuffle(shuffledList);

        var result = new Collectable[collectables.length];
        shuffledList.toArray(result);

        return result;
    }

    public Collectable collectable(int index) {
        return collectables[index];
    }

    private Asset aCollectable() {
        return collectable(0).asset(0);
    }

    private boolean hasAnyCollectable() {
        return !(collectables == null || collectables.length <= 0 || collectable(0).assets().length <= 0);
    }

    private class FileSorter implements Comparator<File> {
        public int compare(File a, File b) {
            return a.getName().compareTo(b.getName());
        }
    }
}
