package com.intelix.net.payload;

import org.apache.commons.collections.primitives.IntList;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class IdNamePayload extends StringPayload {
    private int itemNo;

    public IdNamePayload()
    {
        this(0);
    }

    public IdNamePayload(int length)
    {
        this(length,"",1);
    }

    public IdNamePayload(int length, String str, int itemId)
    {
        super(length,str,1);
        itemNo = itemId;
    }

    @Override
    public IntList encode() {
        IntList encoded = super.encode();
        //try {
        //    encoded.set(0, this.itemNo);
        //} catch (IndexOutOfBoundsException ex) {
            encoded.add(0,itemNo);
        //}
        return encoded;
    }

    @Override
    public void decode(int[] data) {
        super.decode(data);
        this.itemNo = data[0];
    }

    public void setItemNo(int i) {
        this.itemNo = i;
    }

    public int getItemNo() {
        return this.itemNo;
    }
}
