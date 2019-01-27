package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChartVisualizerTest {

    static RecordFactory recordFactory;

    @BeforeAll
    static void fill(){
        recordFactory = new RecordFactory();
    }

    @Test
    void shouldReturnEmptyStringIfThereIsNoRecord(){
        assertEquals("",ChartVisualizer.buildChart(null));
    }

    @Test
    void shouldHaveAuthor() throws Exception {
        String chart = ChartVisualizer.buildChart(new BibParser().parseBibFile("./src/test/java/Data/fullArticle.bib")
                .getRecords().get(RecordType.ARTICLE).get(0));
        assertTrue(chart.contains("AUTHOR"));
        assertTrue(chart.contains("YEAR"));
        assertTrue(chart.contains("JOURNAL"));
        assertTrue(chart.contains("VOLUME"));
        assertTrue(chart.contains("NUMBER"));
        assertTrue(chart.contains("PAGES"));
        assertTrue(chart.contains("NOTE"));
        assertTrue(chart.contains("PAGES"));
        assertTrue(chart.contains("Leslie A. Aamport"));
        assertTrue(chart.contains("article(article-full)"));
        assertFalse(chart.contains("EDITION"));
        assertFalse(chart.contains("EDITOR"));
        assertFalse(chart.contains("SERIES"));



    }
}
