package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.ALabel;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;
import com.baloise.testautomation.taf.swing.base.db.SwError;

public class SwLabel extends ALabel {

  @Override
  public void click() {
    throw new SwError("click on labels not yet supported");
  }

  public ISwLabel<?> find() {
    return (ISwLabel<?>)swFind(ISwLabel.type);
  }

  @Override
  public TafString get() {
    return TafString.normalString(find().getText());
  }

  public void rightClick() {
    find().rightClick();
  }
  
}
