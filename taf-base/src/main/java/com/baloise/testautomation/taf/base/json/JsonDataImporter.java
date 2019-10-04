package com.baloise.testautomation.taf.base.json;

import com.baloise.testautomation.taf.base._base.DataRow;
import com.baloise.testautomation.taf.base._interfaces.IDataImporter;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafString;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertNotNull;

public class JsonDataImporter implements IDataImporter {

    private List<JsonNode> rows = new ArrayList<>();
    private List<String> idFields = Arrays.asList("mandant", "id", "detail");

    public JsonDataImporter(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(file);
            Iterator<JsonNode> elementIterator = rootNode.elements();
            while (elementIterator.hasNext()) {
                rows.add(elementIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull("input stream may not be null", file);
    }


    @Override
    public int getNrOfDataRowsWith(TafId tarifId) {
        assertNotNull("no JSON records found", rows);
        return getWith(tarifId).size();
    }

    @Override
    public Collection<IDataRow> getWith(TafId tafId) {
        List<JsonNode> matchingRecords = new ArrayList<>();
        for (JsonNode element : rows) {
            TafId recordId = getTafId(element);
            if (recordId.equals(tafId)) {
                matchingRecords.add(element);
            }
        }
        return convertToDataRow(matchingRecords);
    }

    private Collection<IDataRow> convertToDataRow(List<JsonNode> records) {
        List<IDataRow> data = new ArrayList<IDataRow>();
        for (JsonNode record : records) {
            Iterator<Map.Entry<String, JsonNode>> fields = record.fields();
            DataRow dataRow = new DataRow();
            dataRow.setId(getTafId(record));
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> next = fields.next();
                if (!idFields.contains(next.getKey())) {
                    dataRow.set(next.getKey(), TafString.normalString(next.getValue().asText()));
                }
            }
            data.add(dataRow);
        }
        return data;
    }

    private TafId getTafId(JsonNode element) {
        String mandant = element.get("mandant").asText();
        String id = element.get("id").asText();
        String detail = element.get("detail").asText();
        return new TafId(mandant, id, detail, null);
    }
}
