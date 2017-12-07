package com.baloise.testautomation.taf.base._base;

import java.lang.annotation.Annotation;

public class AnnotationHelper {

  public static boolean hasAnnotationInHierarchy(Class<?> klass, Class<? extends Annotation> annotationClass) {
    return getAnnotationInHierarchy(klass, annotationClass) != null;
  }

  public static <T extends Annotation> T getAnnotationInHierarchy(Class<?> klass, Class<T> annotationClass) {
    if (klass == null) {
      return null;
    }
    boolean found = false;
    while (!found) {
      T annotation = klass.getAnnotation(annotationClass);
      if (annotation == null) {
        klass = klass.getSuperclass();
        if (klass == null) {
          return null;
        }
      }
      else {
        return annotation;
      }
    }
    return null;
  }

}
