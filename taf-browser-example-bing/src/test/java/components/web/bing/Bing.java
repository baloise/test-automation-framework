/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package components.web.bing;

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

/**
 * 
 */
@DataProvider(DataProviderType.EXCEL)
public class Bing extends ATBEComponent {

  @Fill
  @ById("sb_form_q")
  public BrStringInput search;

  @Data
  public TafString expectedLinkText;

  @ById("sb_form_go")
  public BrButton go;

  public void doSearch(String id) {
    setFill(id);
    fill();
    go.click();
    assertNotNull("Text not found: " + expectedLinkText.asString(),
        getBrowserFinder().findByText(null, expectedLinkText.asString()));
  }

}
