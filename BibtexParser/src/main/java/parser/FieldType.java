package parser;

/**
 * The enum class that contains name of all possible types.
 *
 * @author Dawid Majchrowski
 */
enum FieldType implements Comparable<FieldType> {
    AUTHOR("author"),
    EDITOR("editor"),
    TITLE("title"),
    JOURNAL("journal"),
    YEAR("year"),
    VOLUME("volume"),
    NUMBER("number"),
    PAGES("pages"),
    MONTH("month"),
    NOTE("note"),
    KEY("key"),
    PUBLISHER("publisher"),
    BOOKTITLE("booktitle"),
    SERIES("series"),
    ADDRESS("address"),
    ORGANIZATON("organization"),
    HOWPUBLISHED("howpublished"),
    CHAPTER("chapter"),
    EDITION("edition"),
    SCHOOL("school"),
    INSTITUTION("institution"),
    TYPE("type");
    private String name;

    FieldType(String name) {
        this.name = name;
    }
}
