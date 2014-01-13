/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package be.ac.ulg.montefiore.run.jahmm.apps.cli;

import be.ac.ulg.montefiore.run.jahmm.CentroidFactory;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.Opdf;
import be.ac.ulg.montefiore.run.jahmm.apps.cli.CommandLineArguments.Arguments;
import be.ac.ulg.montefiore.run.jahmm.io.FileFormatException;
import be.ac.ulg.montefiore.run.jahmm.io.HmmReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesWriter;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationWriter;
import be.ac.ulg.montefiore.run.jahmm.io.OpdfReader;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Generates observation sequences from a HMM and write it to file.
 */
class GenerateActionHandler
        extends ActionHandler {

    @Override
    public void act()
            throws FileNotFoundException, IOException, FileFormatException,
            AbnormalTerminationException {
        EnumSet<Arguments> args = EnumSet.of(
                Arguments.OPDF,
                Arguments.OUT_SEQS,
                Arguments.IN_HMM);
        CommandLineArguments.checkArgs(args);

        InputStream hmmStream = Arguments.IN_HMM.getAsInputStream();
        Reader hmmFileReader = new InputStreamReader(hmmStream);
        OutputStream seqsStream = Arguments.OUT_SEQS.getAsOutputStream();
        Writer seqsFileWriter = new OutputStreamWriter(seqsStream);

        write(hmmFileReader, seqsFileWriter, Types.relatedObjs());

        seqsFileWriter.flush();
    }

    private <O extends Observation & CentroidFactory<O>> void
            write(Reader hmmFileReader, Writer seqsFileWriter,
                    RelatedObjs<O> relatedObjs)
            throws IOException, FileFormatException {
        ObservationWriter<O> obsWriter = relatedObjs.observationWriter();
        OpdfReader<? extends Opdf<O>> opdfReader = relatedObjs.opdfReader();
        Hmm<O> hmm = HmmReader.read(hmmFileReader, opdfReader);

        MarkovGenerator<O> generator = relatedObjs.generator(hmm);

        List<List<O>> seqs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            seqs.add(generator.observationSequence(1_000));
        }

        ObservationSequencesWriter.write(seqsFileWriter, obsWriter, seqs);
    }
}