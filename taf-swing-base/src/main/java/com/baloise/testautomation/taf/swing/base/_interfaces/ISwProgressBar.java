package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

public interface ISwProgressBar<R> extends ISwElement<R> {

    public final String type = "progressbar";

    public final String paramText = "text";

    public final String paramValue = "value";

    public final String paramMaximum = "maximum";

    public final String paramMinimum = "minimum";

    public final String paramIsIndeterminate = "isindeterminate";

    public void click();

    public void rightClick();

    public int getValue();

    public int getMaximum();

    public int getMinimum();

    public String getText();

    public boolean isIndeterminate();

    public enum Command {
        gettext, click, getvalue, getmaximum, getminimum, isindeterminate;
    }

}
