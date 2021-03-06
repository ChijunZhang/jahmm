/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.observables;

/**
 * Implements a factory of Gaussian mixtures distributions.
 *
 * @author Benjamin Chung (Creation)
 * @author Jean-Marc Francois (Minor adaptions)
 */
public final class OpdfGaussianMixtureFactory implements OpdfFactory<OpdfGaussianMixture> {

    final private int gaussiansNb;

    /**
     * Creates a new factory of Gaussian mixtures.
     *
     * @param gaussiansNb The number of Gaussian distributions involved in the
     * generated distributions.
     */
    public OpdfGaussianMixtureFactory(int gaussiansNb) {
        this.gaussiansNb = gaussiansNb;
    }

    @Override
    public OpdfGaussianMixture generate() {
        return new OpdfGaussianMixture(gaussiansNb);
    }
}
