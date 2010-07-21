package com.intelix.digihdmi.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Michael Caron <michael.r.caron@gmail.com>
 */
public class JTextFieldLimit extends PlainDocument {
    private int limit;

    public JTextFieldLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;
        int newSize = getLength() + str.length();
        int d = newSize - limit;
        if (newSize <= limit)
        {
            super.insertString(offs, str, a);
        } else
        {
            super.insertString(offs, str.substring(0,str.length()-d), a);
        }
    }
}
