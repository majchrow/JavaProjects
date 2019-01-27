package parser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Concrete AuthorVisitor implementing Visitor interface. Search for all Record of given authors.
 *
 * @author Dawid Majchrowski
 */
class AuthorVisitor implements Visitor {
    private List<String> authors;
    private List<Record> recordTypes;

    public AuthorVisitor(String[] authors) {
        this.authors = (Arrays.asList(authors));
        this.recordTypes = new LinkedList<Record>();
    }

    @Override
    public void visit(Record record) {
        String articleAuthors;
        if(record.getMandatoryFields().keySet().contains(FieldType.AUTHOR)){
            articleAuthors = record.getMandatoryFields().get(FieldType.AUTHOR);
        }else {

            articleAuthors = record.getOptionalFields().get(FieldType.AUTHOR);
        }
        for (String author : authors) {
            if (!articleAuthors.contains(author))
                return;
        }
        recordTypes.add(record);
    }

    public List<Record> getRecordTypes() {
        return recordTypes;
    }
}
