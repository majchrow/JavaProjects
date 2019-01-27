package parser;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class ParserAPITest {
    static ParserAPI parserAPI;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void constructorShouldThrowException(){
        assertThrows(NoSuchFileException.class,()-> new ParserAPI("WrongPathThere"));
    }

    @Test
    void mainMethodShouldRequirePath() throws Exception {
        String []args = {""};
        parserAPI.main(args);
        assertEquals("usage: javac ParserAPI [-a <arg>] [-c <arg>] -p <arg>\n" +
                " -a,--authors <arg>      List of authors\n" +
                " -c,--categories <arg>   List of categories\n" +
                " -p,--path <arg>         Path to file\n", outContent.toString());
    }

    @Test
    void mainMethodShouldReturnOnlyArticles() throws Exception {
        String []args = {"-p", "./src/test/java/Data/test.bib", "-c", "article", "book"};
        parserAPI.main(args);
        assertTrue(outContent.toString().contains("article"));
        assertTrue(outContent.toString().contains("book"));
        assertFalse(outContent.toString().contains("booklet"));
        assertFalse(outContent.toString().contains("inproceedings"));
        assertFalse(outContent.toString().contains("misc"));
    }

    @Test
    void mainMethodShouldReturnOnlyAuthor() throws Exception {
        String []args = {"-p", "./src/test/java/Data/test.bib", "-a", "Ned Net", "Paul Pot"};
        parserAPI.main(args);
        assertTrue(outContent.toString().contains("Ned Net"));
        assertTrue(outContent.toString().contains("Paul Pot"));
        assertFalse(outContent.toString().contains("Jill C. Knvth"));
        assertFalse(outContent.toString().contains("Tom Terrific"));
    }

    @Test
    void mainMethodShouldFindIntersection() throws Exception {
        String []args = {"-p", "./src/test/java/Data/test.bib", "-c", "techreport", "-a", "Tom Terrific"};
        parserAPI.main(args);
        assertTrue(outContent.toString().contains("Tom Terrific"));
        assertTrue(outContent.toString().contains("techreport"));
        assertFalse(outContent.toString().contains("Paul Pot"));
        assertFalse(outContent.toString().contains("book"));
    }

}