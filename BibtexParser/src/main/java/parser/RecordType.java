package parser;


/**
 * The enum class that contains name of all Records.
 *
 * @author Dawid Majchrowski
 */
enum RecordType {
    ARTICLE("article"),
    BOOK("book"),
    INPROCEEDINGS("inproceedings"),
    BOOKLET("booklet"),
    INBOOK("inbook"),
    INCOLLECTION("incollection"),
    MANUAL("manual"),
    MASTERSTHESIS("mastersthesis"),
    PHDTHESIS("phdthesis"),
    TECHREPORT("techreport"),
    MISC("misc"),
    UNPUBLISHED("unpublished");
    public String name;

    RecordType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
