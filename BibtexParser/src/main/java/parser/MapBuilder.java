package parser;

import java.util.TreeMap;

/**
 * The MapBuilder class that create Map with given fields.
 *
 * @author Dawid Majchrowski
 */

class MapBuilder {
    private TreeMap<FieldType, String> result;

    MapBuilder() {
        result = new TreeMap<>();
    }

    MapBuilder withField(FieldType fieldType) {

        result.put(fieldType, "");
        return this;
    }

    TreeMap<FieldType, String> getResult() {
        return result;
    }
}
