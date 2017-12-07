package com.baloise.testautomation.taf.base._base;

import java.lang.annotation.Annotation;
import java.util.List;

public abstract class ElementFinder<T extends Annotation, Element, SearchConstraints, SearchContext> {
    public abstract SearchConstraints getSearchConstraints(T annotation);

    public int getIndex(T annotation) {
        return 0;
    }

    abstract public Element findElement(T annotation, SearchContext searchContext);

    abstract public List<Element> findElements(T annotation, SearchContext searchContext);

    abstract public Class<T> getAnnotationClass();

}
