package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.*;
import org.openqa.selenium.By;

public interface BrElementFinder {
    class ByCssSelectorFinder extends WebElementFinder<ByCssSelector> {
        @Override
        public By getSearchConstraints(ByCssSelector annotation) {
            return By.cssSelector(annotation.value());
        }

        @Override
        public int getIndex(ByCssSelector annotation) {
            return annotation.index();
        }

        @Override
        public Class<ByCssSelector> getAnnotationClass() {
            return ByCssSelector.class;
        }
    }

    class ByIdFinder extends WebElementFinder<ById> {
        @Override
        public By getSearchConstraints(ById annotation) {
            return By.id(annotation.value());
        }

        @Override
        public int getIndex(ById annotation) {
            return annotation.index();
        }

        @Override
        public Class<ById> getAnnotationClass() {
            return ById.class;
        }
    }

    class ByNameFinder extends WebElementFinder<ByName> {
        @Override
        public By getSearchConstraints(ByName annotation) {
            return By.name(annotation.value());
        }

        @Override
        public int getIndex(ByName annotation) {
            return annotation.index();
        }

        @Override
        public Class<ByName> getAnnotationClass() {
            return ByName.class;
        }
    }

    class ByTextFinder extends WebElementFinder<ByText> {
        @Override
        public By getSearchConstraints(ByText annotation) {
            return By.xpath("//*[contains(text(), \"" + annotation.value() + "\")]");
        }

        @Override
        public int getIndex(ByText annotation) {
            return annotation.index();
        }

        @Override
        public Class<ByText> getAnnotationClass() {
            return ByText.class;
        }
    }

    class ByXpathFinder extends WebElementFinder<ByXpath> {
        @Override
        public By getSearchConstraints(ByXpath annotation) {
            return By.xpath(annotation.value());
        }

        @Override
        public int getIndex(ByXpath annotation) {
            return annotation.index();
        }

        @Override
        public Class<ByXpath> getAnnotationClass() {
            return ByXpath.class;
        }
    }

    class ByLeftlabelFinder extends WebElementFinder<ByLeftLabel> {
        @Override
        public By getSearchConstraints(ByLeftLabel annotation) {
            return By.xpath("//*[@leftlabel=\"" + annotation.value() + "\"]");
        }

        @Override
        public int getIndex(ByLeftLabel annotation) {
            return annotation.index();
        }

        @Override
        public Class<ByLeftLabel> getAnnotationClass() {
            return ByLeftLabel.class;
        }
    }

}
