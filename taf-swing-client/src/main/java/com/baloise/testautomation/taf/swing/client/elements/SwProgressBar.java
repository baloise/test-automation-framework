package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwProgressBar;

public class SwProgressBar extends AElement {

    @Override
    public void click() {
        find().click();
    }

    public ISwProgressBar<?> find() {
        return (ISwProgressBar<?>) swFind(ISwProgressBar.type);
    }

    public void rightClick() {
        find().rightClick();
    }

    public String getText() {
        return find().getText();
    }

    public int getValue() {
        return find().getValue();
    }

    public int getMaximum() {
        return find().getMaximum();
    }

    public int getMinimum() {
        return find().getMinimum();
    }

    public boolean isIndeterminate() {
        return find().isIndeterminate();
    }

}
