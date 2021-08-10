package com.baloise.testautomation.taf.base._base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TafError extends Error {

    private Annotation byAnnotation = null;

    public TafError(String msg) {
        this(msg, null);
    }

    public TafError(String msg, Annotation causeAnnotation) {
        super(msg);
        byAnnotation = causeAnnotation;
    }

    public static String getByInfo(Annotation aAnnotation) {
        if (aAnnotation != null) {
            try {
                Method valueMethod = aAnnotation.getClass().getMethod("value");
                if (valueMethod != null)
                    return " is caused byAnnotation: " + aAnnotation.getClass().getInterfaces()[0].getSimpleName() + " -> " + valueMethod.invoke(aAnnotation);
            } catch (Exception e) {
            }
        }
        return "";
    }

    public String getByInfo() {
        return getByInfo(byAnnotation);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + getByInfo();
    }

    @Override
    public String toString() {
        return super.toString() + getByInfo(byAnnotation);
    }
}
