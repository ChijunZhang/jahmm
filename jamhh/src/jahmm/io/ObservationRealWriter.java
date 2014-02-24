/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.io;

import jahmm.observables.ObservationReal;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes an {@link ObservationReal ObservationReal} up to (and including) the
 * semi-colon.
 */
public class ObservationRealWriter
        extends ObservationWriter<ObservationReal> {

    @Override
    public void write(ObservationReal observation, Writer writer)
            throws IOException {
        writer.write(observation.value + "; ");
    }
}
