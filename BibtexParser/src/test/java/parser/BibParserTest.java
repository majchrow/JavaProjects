package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BibParserTest {

    static BibParser bibParser;

    @BeforeAll
    static void fill(){
        bibParser = new BibParser();
    }

    @Test
    public void readToStringShouldThrowException(){
        assertThrows(NoSuchFileException.class,()-> bibParser.readToString("There is wrong path"));
    }
    @Test
    public void readToStringShouldReturnEmptyString() throws IOException {
        assertEquals("",bibParser.readToString("./src/test/java/Data/empty.bib"));
    }

    @Test
    public void readToStringShouldReadWholeFile() throws IOException {
        assertEquals(22,bibParser.readToString("./src/test/java/Data/readString.bib").length());
    }

    @Test
    public void filterShouldReturnEmptyLinkedList() throws IOException {
        assertEquals(new LinkedList<>(),bibParser.filter(
                bibParser.readToString("./src/test/java/Data/onlyNoise.bib"),new BibObject()));
    }

    @Test
    public void filterShouldntReturnEmptyLinkedList() throws IOException {
        assertEquals(1,bibParser.filter(
                bibParser.readToString("./src/test/java/Data/onlyArticle.bib"),new BibObject()).size());
    }

    @Test
    public void filterShouldFindStringAnnotation() throws IOException {
        BibObject bibObject = new BibObject();
        bibParser.filter(bibParser.readToString("./src/test/java/Data/onlyAnnotations.bib"),bibObject);
        assertEquals(3,bibObject.getStringAddnotations().size());
        assertTrue(bibObject.getStringAddnotations().containsValue("value"));
        assertTrue(bibObject.getStringAddnotations().containsKey("key"));
    }

    @Test
    public void clearStringTest(){
        String authors = "Bragueta Suelta | Antonio and Emiliano Salido del Pozo and Trabajo Cumplido | Cumplido";
        String expected = "Antonio Bragueta Suelta\nEmiliano Salido del Pozo\nCumplido Trabajo Cumplido";
        assertEquals(expected,bibParser.clearString(authors));
    }

}