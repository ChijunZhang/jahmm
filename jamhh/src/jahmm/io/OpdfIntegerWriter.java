/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.io;

import jahmm.ObservationInteger;
import jahmm.OpdfInteger;
import java.io.IOException;
import java.io.Writer;

/**
 * This class can write a textual description of an {@link OpdfInteger}. It is
 * compatible with {@link OpdfIntegerReader}.
 */
public class OpdfIntegerWriter
        extends OpdfWriter<OpdfInteger> {

    @Override
    public void write(Writer writer, OpdfInteger opdf)
            throws IOException {
        String s = "IntegerOPDF [";

        for (int i = 0; i < opdf.nbEntries(); i++) {
            s += opdf.probability(new ObservationInteger(i)) + " ";
        }

        writer.write(s + "]\n");
    }
}
