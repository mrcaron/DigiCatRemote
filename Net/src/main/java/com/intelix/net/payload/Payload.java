package com.intelix.net.payload;

import java.lang.reflect.Constructor;
import java.util.ResourceBundle;
import org.apache.commons.collections.primitives.IntList;

public abstract class Payload {

    private static ResourceBundle types;

    private static ResourceBundle getTypesConfiguration() {
        if (types == null) {
            types = ResourceBundle.getBundle("com.intelix.net.payload.typesConfiguration");
        }
        return types;
    }

    public static Payload create(int classNo, int idNo) {
        types = getTypesConfiguration();
        if (types != null) {
            String key = classNo + "." + idNo;
            if (types.containsKey(key)) {
                try {
                    String className = types.getString(key);
                    Class cls = Class.forName(className);
                    Constructor ctor = cls.getConstructor(new Class[0]);
                    return (Payload) ctor.newInstance(new Object[0]);
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public abstract IntList encode();

    public abstract void decode(int[] paramArrayOfInt);
}


