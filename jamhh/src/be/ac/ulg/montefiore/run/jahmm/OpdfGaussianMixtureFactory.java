/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package be.ac.ulg.montefiore.run.jahmm;

import java.util.logging.Logger;

/**
 * Implements a factory of Gaussian mixtures distributions.
 *
 * @author Benjamin Chung (Creation)
 * @author Jean-Marc Francois (Minor adaptions)
 */
public class OpdfGaussianMixtureFactory
        implements OpdfFactory<OpdfGaussianMixture> {

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
    public OpdfGaussianMixture factor() {
        return new OpdfGaussianMixture(gaussiansNb);
    }
    private static final Logger LOG = Logger.getLogger(OpdfGaussianMixtureFactory.class.getName());
}
