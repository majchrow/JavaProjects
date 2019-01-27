package parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordFactoryTest {
    static RecordFactory recordFactory;

    @BeforeAll
    static void fill() {
        recordFactory = new RecordFactory();
    }
    @Test
    void shouldReturnArticleClass(){
        assertEquals(Article.class, recordFactory.getRecord(RecordType.ARTICLE).getClass());
    }

    @Test
    void shouldReturnBookClass(){
        assertEquals(Book.class, recordFactory.getRecord(RecordType.BOOK).getClass());
    }

    @Test
    void shouldReturnMiscClass(){
        assertEquals(Misc.class, recordFactory.getRecord(RecordType.MISC).getClass());
    }

    @Test
    void shouldContainAuthorField(){
        assertTrue(recordFactory.getRecord(RecordType.ARTICLE).getMandatoryFields().containsKey(FieldType.AUTHOR));
        assertTrue(recordFactory.getRecord(RecordType.BOOK).getMandatoryFields().containsKey(FieldType.AUTHOR));
        assertTrue(recordFactory.getRecord(RecordType.INPROCEEDINGS).getMandatoryFields().containsKey(FieldType.AUTHOR));
        assertTrue(recordFactory.getRecord(RecordType.BOOKLET).getOptionalFields().containsKey(FieldType.AUTHOR));
        assertTrue(recordFactory.getRecord(RecordType.MANUAL).getOptionalFields().containsKey(FieldType.AUTHOR));

    }

}