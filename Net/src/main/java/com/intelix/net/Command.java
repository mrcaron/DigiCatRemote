package com.intelix.net;

import com.intelix.net.exceptions.InsufficientDataException;
import com.intelix.net.exceptions.UnidentifiedMessageException;
import com.intelix.net.payload.Payload;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntIterator;

public class Command {

    protected static final int MSG_MAX = 256;
    int classNo;
    int idNo;
    Payload payload;
    int logicalLength;
    int actualLength;

    public Command() {
        this.payload = null;
    }

    public int getClassNo() {
        return this.classNo;
    }

    public int getIdNo() {
        return this.idNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public void setIdNo(int idNo) {
        this.idNo = idNo;
    }

    public byte[] encode() {
        ArrayIntList msg = new ArrayIntList();
        msg.add(this.classNo);
        msg.add(this.idNo);

        if (this.payload != null) {
            msg.addAll(this.payload.encode());
        }
        int length = msg.size();
        msg.add(0, length);

        IntIterator it = msg.iterator();
        ArrayByteList msgBytes = new ArrayByteList();
        msgBytes.add((byte) 0xf1);
        while (it.hasNext()) {
            int val = it.next();
            if (val >= 0xf0) {
                msgBytes.add((byte) 0xf0);
                msgBytes.add((byte) (val - 0xf0));
            } else {
                msgBytes.add((byte) val);
            }
        }

        return msgBytes.toArray();
    }

    public int decode(byte[] bytes) throws InsufficientDataException, UnidentifiedMessageException {
        return decode(bytes, 0);
    }

    public boolean hasPayload() {
        return this.payload != null;
    }

    public void setPayload(Payload p) {
        this.payload = p;
    }

    public Payload getPayload() {
        return this.payload;
    }

    int decode(byte[] bytes, int position) throws InsufficientDataException, UnidentifiedMessageException {
        
        int index = 0;

        try {
            index = position;

            while (bytes[index] != -15 && index<bytes.length) {
                ++index;
            }
        } catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new UnidentifiedMessageException("Received unknown message.", ex);
        }
        
        try
        {
            int msgBegin = index;

            ++index;

            this.logicalLength = (0xFF & bytes[(index++)]);
            if (this.logicalLength >= 240) {
                this.logicalLength += bytes[(index++)];
            }

            int bodyStart = index;
            this.classNo = bytes[(index++)];
            if (this.classNo >= 240) {
                this.classNo += bytes[(index++)];
            }

            this.idNo = bytes[(index++)];
            if (this.idNo >= 240) {
                this.idNo += bytes[(index++)];
            }

            this.payload = Payload.create(this.classNo, this.idNo);
            if (this.payload != null) {
                int payloadStart = index;
                int[] payloadData = new int[this.logicalLength - 2];
                for (int i = 0; i < payloadData.length; ++i) {
                    int val = 0xFF & bytes[(index++)];
                    val = (val >= 240) ? val + bytes[(index++)] : val;
                    payloadData[i] = val;
                }
                this.payload.decode(payloadData);
            }

            int msgEnd = index;
            this.actualLength = (msgEnd - msgBegin);

            return msgEnd;
        } catch (IndexOutOfBoundsException ex) {
            throw new InsufficientDataException();
        }
    }

    int getActualSize() {
        return this.actualLength;
    }
}
