/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.io;

import jahmm.observables.ObservationInteger;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes an {@link ObservationInteger ObservationInteger} up to (and including)
 * the semi-colon.
 */
public class ObservationIntegerWriter
        extends ObservationWriter<ObservationInteger> {

    @Override
    public void write(ObservationInteger observation, Writer writer)
            throws IOException {
        writer.write(observation.value + "; ");
    }
}
