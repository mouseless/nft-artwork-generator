package com.hipsteranimals.nag;

public class Collectable {
    private final Asset[] assets;

    public Collectable(Asset asset) {
        this.assets = new Asset[] { asset };
    }

    public Collectable(Asset asset, Collectable collectable) {
        this.assets = new Asset[collectable.assets.length + 1];

        assets[0] = asset;
        
        for (var i = 0; i < collectable.assets.length; i++) {
            assets[i + 1] = collectable.assets[i];
        }
    }

    public Asset[] assets() {
        return assets;
    }
    
    public Asset asset(int index) {
        return assets[index];
    }
}
