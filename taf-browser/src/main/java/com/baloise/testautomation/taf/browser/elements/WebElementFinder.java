package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._base.ElementFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.annotation.Annotation;
import java.util.List;

public abstract class WebElementFinder<T extends Annotation> extends ElementFinder<T, WebElement, By, SearchContext> {
    @Override
    public WebElement findElement(T annotation, SearchContext searchContext) {
        final List<WebElement> elements = findElements(annotation, searchContext);
        return (elements != null && elements.size() > getIndex(annotation)) ? elements.get(getIndex(annotation)) : null;
    }

    @Override
    public List<WebElement> findElements(T annotation, SearchContext searchContext) {
        return searchContext.findElements(getSearchConstraints(annotation));
    }
}
