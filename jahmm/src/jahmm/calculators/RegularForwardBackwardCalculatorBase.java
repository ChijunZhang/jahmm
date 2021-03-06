/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.calculators;

import jahmm.RegularHmm;
import jahmm.observables.Observation;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class can be used to compute the probability of a given observations
 * sequence for a given HMM. Once the probability has been computed, the object
 * holds various information such as the <i>alpha</i> (and possibly
 * <i>beta</i>) array, as described in <i>Rabiner</i> and <i>Juang</i>.
 * <p>
 * Computing the <i>beta</i> array requires a O(1) access time to the
 * observation sequence to get a theoretically optimal performance.
 *
 * @param <TObs>
 */
public class RegularForwardBackwardCalculatorBase<TObs extends Observation, THmm extends RegularHmm<TObs,THmm>> extends ForwardBackwardCalculatorRaw<double[][], double[][], TObs, TObs, THmm> implements RegularForwardBackwardCalculator<TObs,THmm> {

    public static final RegularForwardBackwardCalculatorBase Instance = new RegularForwardBackwardCalculatorBase();
    private static final Logger LOG = Logger.getLogger(RegularForwardBackwardCalculatorBase.class.getName());

    /**
     *
     */
    protected RegularForwardBackwardCalculatorBase() {
    }

    @Override
    protected double computeProbability(List<? extends TObs> oseq, THmm hmm, Collection<ComputationType> flags, double[][] alpha, double[][] beta) {
        double probability = 0.;
        int n = hmm.nbStates();
        double[] tmp;
        if (flags.contains(ComputationType.ALPHA)) {
            tmp = alpha[oseq.size() - 1];
            for (int i = 0; i < n; i++) {
                probability += tmp[i];
            }
        } else {
            tmp = beta[0x00];
            TObs observation = oseq.get(0x00);
            for (int i = 0; i < n; i++) {
                probability += hmm.getPi(i) * hmm.getOpdf(i).probability(observation) * tmp[i];
            }
        }
        return probability;
    }

    /**
     * Computes the content of the alpha array
     *
     * @param <TObs>
     * @param hmm
     * @param oseq
     * @return alpha[t][i] = P(O(1), O(2),..., O(t+1), i(t+1) = i+1 | hmm), that
     * is the probability of the beginning of the state sequence (up to time
     * t+1) with the (t+1)th state being i+1.
     */
    @Override
    public double[][] computeAlpha(THmm hmm, Collection<? extends TObs> oseq) {
        int T = oseq.size();
        int s = hmm.nbStates();
        double[][] alpha = new double[T][s];
        T--;

        Iterator<? extends TObs> seqIterator = oseq.iterator();
        TObs observation;
        if (seqIterator.hasNext()) {
            observation = seqIterator.next();

            for (int i = 0; i < hmm.nbStates(); i++) {
                alpha[0][i] = hmm.getPi(i) * hmm.getOpdf(i).probability(observation);
            }

            for (int t = 0; t < T; t++) {
                observation = seqIterator.next();

                for (int j = 0; j < s; j++) {
                    double sum = 0.;
                    for (int i = 0; i < s; i++) {
                        sum += alpha[t][i] * hmm.getAij(i, j);
                    }
                    alpha[t + 0x01][j] = sum * hmm.getOpdf(j).probability(observation);
                }
            }
        }
        return alpha;
    }

    /* Computes the content of the beta array.  Needs a O(1) access time
     to the elements of oseq to get a theoretically optimal algorithm. */
    @Override
    public double[][] computeBeta(THmm hmm, List<? extends TObs> oseq) {
        int t = oseq.size();
        int s = hmm.nbStates();
        double[][] beta = new double[t][s];
        t--;
        TObs observation;

        for (int i = 0; i < s; i++) {
            beta[t][i] = 1.0d;
        }

        for (; t > 0;) {
            for (int i = 0; i < s; i++) {
                observation = oseq.get(t);
                double sum = 0.0d;
                for (int j = 0; j < s; j++) {
                    sum += beta[t][j] * hmm.getAij(i, j) * hmm.getOpdf(j).probability(observation);
                }
                beta[t - 0x01][i] = sum;
            }
            t--;
        }
        return beta;
    }
}
