package com.baloise.testautomation.taf.base;

import com.baloise.testautomation.taf.base.csv.CsvDataImporter;
import com.baloise.testautomation.taf.base.json.JsonDataImporter;
import com.baloise.testautomation.taf.base.types.TafId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class TestJsonDataLoader {

    private static File file;
    private JsonDataImporter jsonDataImporter;

    @BeforeClass
    public static void init() throws URISyntaxException {
        file = new File(TestCsvDataLoader.class.getResource("TestJsonDataLoader.json").toURI());
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

}
