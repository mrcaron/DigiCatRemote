package com.intelix.net.payload;

public class StringPayload extends SequencePayload {

    static int spaceChar = " ".charAt(0);

    String strData;

    public StringPayload() {
        super();
    }

    public StringPayload(int length, String str)
    {
        this(length,str,0);
    }

    public StringPayload(int length, String str, int startIdx) {
        super(length,startIdx);

        strData = str;

        for(int i=0; i<length; i++)
        {
            try {
               add(str.charAt(i));
            } catch (StringIndexOutOfBoundsException ex)
            {
                add(spaceChar);
            }
        }
    }

    @Override
    public void decode(int[] data) {
        length = data.length;
        StringBuffer b = new StringBuffer(data.length - this.startIdx);
        for (int i = startIdx; i < this.length; ++i) {
            b.append((char) data[i]);
        }
        strData = b.toString();
    }

    public String getStrData()
    {
        return strData.trim();
    }
}
