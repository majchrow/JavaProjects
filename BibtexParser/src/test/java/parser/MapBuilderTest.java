package parser;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapBuilderTest {

    @Test
    void shouldReturnEmptyMap() {
        MapBuilder mapBuilder = new MapBuilder();
        assertEquals(new TreeMap<FieldType, String>(), mapBuilder.getResult());
    }

    @Test
    void shouldContaintOneFieldAndEmptyValue() {
        MapBuilder mapBuilder = new MapBuilder();
        Map<FieldType, String> map = mapBuilder.withField(FieldType.AUTHOR).getResult();
        assertTrue(map.containsKey(FieldType.AUTHOR));
        assertTrue(map.values().contains(""));
    }


}