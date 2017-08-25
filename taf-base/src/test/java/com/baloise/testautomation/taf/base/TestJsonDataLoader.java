package com.baloise.testautomation.taf.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.json.JsonDataImporter;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafString;

public class TestJsonDataLoader {

    private static File file;
    private JsonDataImporter jsonDataImporter;

    @BeforeClass
    public static void init() throws URISyntaxException {
        file = new File(TestJsonDataLoader.class.getResource("TestJsonDataLoader.json").toURI());
    }

    @Before
    public void setUp() {
        jsonDataImporter = new JsonDataImporter(file);
    }

    @Test
    public void nrOfRows() {
        assertEquals(1, jsonDataImporter.getNrOfDataRowsWith(new TafId("int", "test", "1")));
        assertEquals(1, jsonDataImporter.getNrOfDataRowsWith(new TafId("int", "TEST", "2")));
        assertEquals(0, jsonDataImporter.getNrOfDataRowsWith(new TafId("inti", "TEST", "2")));
    }

    @Test
    public void openWithFile() {
        assertEquals(1, jsonDataImporter.getNrOfDataRowsWith(new TafId("int", "test", "1")));
    }

    @Test
    public void rowContents1() {
        Collection<IDataRow> data = jsonDataImporter.getWith(new TafId("int", "TEst", "1"));
        assertEquals(1, data.size());
        Vector<IDataRow> vData = new Vector<>();
        vData.addAll(data);
        assertEquals(TafString.class, vData.get(0).get("INTEGER").getClass());
        assertEquals(TafString.class, vData.get(0).get("double").getClass());
        assertEquals(TafString.class, vData.get(0).get("date").getClass());
        assertEquals(TafString.class, vData.get(0).get("string").getClass());
        assertEquals(TafString.class, vData.get(0).get("boolean").getClass());
    }

    @Test
    public void rowContents2() {
        Collection<IDataRow> data = jsonDataImporter.getWith(new TafId("int", "TEst", "2"));
        assertEquals(1, data.size());
        Vector<IDataRow> vData = new Vector<>();
        vData.addAll(data);
        assertEquals(2, vData.get(0).get("INTEGER").asInteger().intValue());
        assertEquals(new Double(2.2), vData.get(0).get("Double").asDouble());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            System.out.println(vData.get(0).get("date").asDate());
            assertEquals(sdf.parse("02.02.2000"), vData.get(0).get("date").asDate());
        }
        catch (ParseException e) {
            fail();
        }
        assertEquals("Zwei String", vData.get(0).get("string").asString());
        assertEquals(false, vData.get(0).get("boolean").asBoolean());
    }

    @Test
    public void rowContents3() throws URISyntaxException {
        Collection<IDataRow> data = jsonDataImporter.getWith(new TafId("test", "othertest", ""));
        assertEquals(1, data.size());
        Vector<IDataRow> vData = new Vector<>();
        vData.addAll(data);
        assertTrue(vData.get(0).get("INTEGER").isNull());
        assertTrue(vData.get(0).get("double").isSkip());
        assertTrue(vData.get(0).get("date").isEmpty());
        assertEquals(TafString.class, vData.get(0).get("string").getClass());
        assertEquals(TafString.class, vData.get(0).get("boolean").getClass());

    }

    @Test
    public void rowContents4() {
        Collection<IDataRow> data = jsonDataImporter.getWith(new TafId("test", "string", ""));
        assertEquals(1, data.size());
        Vector<IDataRow> vData = new Vector<>();
        vData.addAll(data);
        assertTrue(vData.get(0).get("INTEGER") instanceof TafString);
        assertTrue(vData.get(0).get("double") instanceof TafString);

    }

    @Test
    public void rowTafId() {
        Collection<IDataRow> data = jsonDataImporter.getWith(new TafId("int", "TEst", ""));
        assertEquals(2, data.size());
        Vector<IDataRow> vData = new Vector<>();
        vData.addAll(data);
        System.out.println(vData.get(0).getId().asIdDetailString());
        assertTrue(vData.get(0).getId().asIdDetailString().equalsIgnoreCase("test-1"));
        assertTrue(vData.get(1).getId().asIdDetailString().equalsIgnoreCase("test-2"));
    }

}
