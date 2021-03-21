package com.hipsteranimals.nag;

import java.io.File;
import java.io.FilenameFilter;
import static com.hipsteranimals.nag.Preferences.*;

public class Category {
    private final Asset[] assets;
    private Category next;

    public Category(File folder) {
        var files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });

        if (TEST_MODE) {
            assets = new Asset[1];
        } else {
            assets = new Asset[files.length];
        }

        for (var i = 0; i < assets.length; i++) {
            assets[i] = new Asset(this, files[i]);
        }
    }

    public Asset[] assets() {
        return assets;
    }

    public void next(Category next) {
        this.next = next;
    }

    public Category next() {
        return next;
    }

    public Collectable[] build() {
        Collectable[] result;

        if (next == null) {
            result = new Collectable[assets.length];

            for (var i = 0; i < assets.length; i++) {
                result[i] = new Collectable(assets[i]);
            }
        } else {
            var collectables = next.build();
            result = new Collectable[assets.length * collectables.length];

            for (var i = 0; i < assets.length; i++) {
                for (var j = 0; j < collectables.length; j++) {
                    result[i * collectables.length + j] = new Collectable(assets[i], collectables[j]);
                }
            }
        }

        return result;
    }
}
