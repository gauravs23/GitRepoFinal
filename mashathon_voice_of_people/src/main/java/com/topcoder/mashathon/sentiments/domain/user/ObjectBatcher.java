/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mashathon.sentiments.domain.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Really simple whose sole purpose is to consume a stream (via an {@link Iterable}) of objects of type <code>T</code>,
 * and to separate that stream into "batches" (Lists) of a predetermined size. When a batch is created,
 * {@link Handler#handleBatch(List)} is called with the membersof the batch.
 * <p/>
 * This allows us to, for example, reduce a single stream of 1000 objects into 10 lists of 100 objects each. We could,
 * then, send each batch in the {@link Handler#handleBatch(List)} method, off for processing asynchronously someplace.
 * 
 * @author TCASSEMBLER
 * @since 1.0
 * @version 1.0
 * @param <T>
 *            the type of object being batched
 */
public class ObjectBatcher<T> {
    /**
     * Handler implementations are responsible for "handling" batches as they are realized.
     * 
     * @param <T>
     *            the type of object being batched
     */
    public interface Handler<T> {
        /**
         * Implementation must "handle" this batch (list) in the appropriate way.
         * 
         * @param batch
         *            the batch (list of objects) to handle
         * @param lastBatch
         *            is this the last batch to handle?
         */
        void handleBatch(List<T> batch, boolean lastBatch);
    }

    /**
     * Handler implementations are responsible for filtering unwanted entries in the batches, as they are being put
     * together.
     * 
     * @param <T>
     *            the type of object being batched
     */
    public interface Filter<T> {
        /**
         * Whether to include this object, t, in a batch
         * 
         * @param t
         *            the object to evaluate
         * @return whether it should be included or not
         */
        boolean include(T t);
    }

    /**
     * Processes the stream of <code>T</code> objects in <code>iterable</code> into batches of size
     * <code>perBatch</code>. When a batch is realized, it is sent to {@link Handler#handleBatch(List)} for
     * handling/processing.
     * 
     * @param iterable
     *            the iterable to consume the T objects from
     * @param perBatch
     *            the maximum number of items per batch
     * @param filter
     *            the filter to use to determine whether an object should be in the batch or not
     * @param handler
     *            the handler that processes batches.
     * @throws IllegalArgumentException
     *             if iterable is null
     */
    public void batch(Iterable<T> iterable, int perBatch, Filter<T> filter, Handler<T> handler) {
        if (iterable == null) {
            throw new IllegalArgumentException("iterable must not be null");
        }

        List<T> thisBatch = new ArrayList<T>();

        for (T t : iterable) {
            if (!filter.include(t)) {
                continue;
            }

            thisBatch.add(t);

            if (thisBatch.size() == perBatch) {
                handler.handleBatch(thisBatch, false);
                thisBatch = new ArrayList<T>();
            }
        }

        handler.handleBatch(thisBatch, true);
    }
}
