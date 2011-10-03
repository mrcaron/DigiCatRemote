package com.intelix.net.payload;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;

/**
 * PairSequencePayload can be used as a Sequence payload by omitting the
 * values when adding items. But avoid mixing pairs with regular items!
 * 
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class PairSequencePayload extends Payload {

    HashMap<Integer, Integer> pairs;

    public PairSequencePayload() {
        pairs = new HashMap(10);
    }

    public void add(int first, int second) {
        pairs.put(second, first);
    }

    public int get(int output) {
        return pairs.get(new Integer(output));
    }

    @Override
    public IntList encode() {
        ArrayIntList encoded = new ArrayIntList(pairs.size() * 2);
        for (Map.Entry<Integer,Integer> pair : pairs.entrySet()) {

            int v = pair.getValue() != null ? pair.getValue().intValue() : 0;
            int k = pair.getKey() != null ? pair.getKey().intValue() : 0;

            if (v > 0) encoded.add( pair.getValue() );
            if (k > 0) encoded.add( pair.getKey() );
        }
        return encoded;
    }

    @Override
    public void decode(int[] data) {
        for (int i = 0; i < data.length; ++i) {
            int input = data[(i++)];
            int output = data[i];
            pairs.put(output, input);
        }
    }
}
