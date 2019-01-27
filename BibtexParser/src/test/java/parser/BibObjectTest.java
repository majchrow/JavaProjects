package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BibObjectTest {
    static BibObject bibObject;

    @BeforeAll
    static void fill() throws Exception {
        bibObject = new BibParser().parseBibFile("./src/test/java/Data/test.bib");
    }

    @Test
    void shouldReturnEmptyList() {
        String[] strings = {"jeden", "dwa", "trzy"};
        assertEquals(new LinkedList<>(), bibObject.getRecordByAuthors(strings));

    }

    @Test
    void shouldReturnOneRecord() {
        String[] strings = {"Ned Net", "Paul Pot"};
        assertEquals(1, bibObject.getRecordByAuthors(strings).size());

    }

}