/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.net.payload;

import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;

/**
 * Holds a sequence of numbers
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class SequencePayload extends Payload {
    protected int length;
    protected ArrayIntList data;
    protected int startIdx;

    public SequencePayload()
    {
        this(100 /* MRC. 100 is an arbitrary choice */,0);
    }

    public SequencePayload(int length) {
        this(length,0);
    }
    
    public SequencePayload(int length, int startIdx)
    {
        this.length = length;
        this.startIdx = startIdx;
        data = new ArrayIntList(length);
    }

    public void add(int i)
    {
        data.add(i);
    }

    public int get(int i)
    {
        return data.get(i);
    }

    public ArrayIntList getData() {
        return data;
    }

    public int size() {
        return data.size();
    }

    @Override
    public IntList encode() {
        return data;
    }

    @Override
    public void decode(int[] data) {
        length = data.length;
        for (int i = startIdx; i < this.length - this.startIdx; ++i) {
            this.data.add(data[i]);
        }
    }
}
