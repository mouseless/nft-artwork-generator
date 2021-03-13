package com.ikinoktabir.nag;

public class Combination {
    private final Sample[] samples;

    public Combination(Sample sample) {
        this.samples = new Sample[] { sample };
    }

    public Combination(Sample sample, Combination combination) {
        this.samples = new Sample[combination.samples.length + 1];

        samples[0] = sample;
        
        for (var i = 0; i < combination.samples.length; i++) {
            samples[i + 1] = combination.samples[i];
        }
    }

    public Sample[] samples() {
        return samples;
    }
}
