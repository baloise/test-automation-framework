package eclipse.jubula.auts;

import static org.junit.Assert.assertNotNull;
import base.ATBEComponent;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ById;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Data;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.DataProvider;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.DataProviderType;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Fill;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.browser.elements.BrButton;
import com.baloise.testautomation.taf.browser.elements.BrStringInput;

@DataProvider(DataProviderType.EXCEL)
public class SimpleAdder extends ATBEComponent {
  @Fill
  @ById("value1")
  public BrStringInput value1;

  @Fill
  @ById("value2")
  public BrStringInput value2;

  @ById("submit")
  public BrButton submit;
  
  @Data
  public TafString result;

  public void doAddition(String id) {
    setFill(id);
    fill();
    submit.click();
    assertNotNull("Text not found: " + result.asString(),
        getBrowserFinder().findByText(null, result.asString()));
  }
}