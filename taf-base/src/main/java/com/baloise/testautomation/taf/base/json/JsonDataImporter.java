package com.baloise.testautomation.taf.base.json;

import com.baloise.testautomation.taf.base._interfaces.IDataImporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.types.TafId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;

public class JsonDataImporter implements IDataImporter {

    private final File file;

    public JsonDataImporter(File file) {
        this.file = file;
        assertNotNull("input stream may not be null", file);
    }


    @Override
    public int getNrOfDataRowsWith(TafId tarifId) {
        ObjectMapper mapper = new ObjectMapper();
        int rows = 0;
        try {
            JsonNode rootNode = mapper.readTree(this.file);
            Iterator<JsonNode> elementIterator = rootNode.elements();
            while (elementIterator.hasNext()) {
                JsonNode element = elementIterator.next();
                String mandant = element.get("mandant").asText();
                String id = element.get("id").asText();
                String detail = element.get("detail").asText();
                TafId recordId = new TafId(mandant, id, detail, null);
                if (recordId.equals(tarifId)) {
                    rows++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public Collection<IDataRow> getWith(TafId id) {
        return null;
    }
}
