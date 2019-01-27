package parser;
/**
 * The RecordFactor class that create concrete Record using MapBuilder.
 *
 * @author Dawid Majchrowski
 */
class RecordFactory {
    Record getRecord(RecordType recordType) {
        MapBuilder optional = new MapBuilder();
        MapBuilder mandatory = new MapBuilder();
        switch (recordType) {
            case ARTICLE:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.JOURNAL).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.VOLUME).withField(FieldType.NUMBER).withField(FieldType.PAGES).
                        withField(FieldType.MONTH).withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Article()
                        .setMandatoryFields(mandatory.getResult())
                        .setOptionalFields(optional.getResult());
            case BOOK:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.EDITOR).withField(FieldType.TITLE).
                        withField(FieldType.PUBLISHER).withField(FieldType.YEAR);
                optional.withField(FieldType.VOLUME).withField(FieldType.SERIES).withField(FieldType.ADDRESS).
                        withField(FieldType.EDITION).withField(FieldType.MONTH).withField(FieldType.NOTE).
                        withField(FieldType.KEY);
                return new Book().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case INPROCEEDINGS:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.BOOKTITLE).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.EDITOR).withField(FieldType.VOLUME).withField(FieldType.NUMBER).
                        withField(FieldType.SERIES).withField(FieldType.PAGES).withField(FieldType.ADDRESS).
                        withField(FieldType.MONTH).withField(FieldType.ORGANIZATON).withField(FieldType.PUBLISHER).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Inproceedings().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case BOOKLET:
                mandatory.withField(FieldType.TITLE);
                optional.withField(FieldType.AUTHOR).withField(FieldType.HOWPUBLISHED).withField(FieldType.ADDRESS).
                        withField(FieldType.MONTH).withField(FieldType.YEAR).withField(FieldType.NOTE).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Booklet().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case INBOOK:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.EDITOR).withField(FieldType.TITLE).
                        withField(FieldType.CHAPTER).withField(FieldType.PAGES).withField(FieldType.PUBLISHER).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.VOLUME).withField(FieldType.NUMBER).withField(FieldType.TYPE).
                        withField(FieldType.ADDRESS).withField(FieldType.EDITION).withField(FieldType.MONTH).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Inbook().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case INCOLLECTION:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.BOOKTITLE).
                        withField(FieldType.PUBLISHER).withField(FieldType.YEAR);
                optional.withField(FieldType.EDITOR).withField(FieldType.VOLUME).withField(FieldType.NUMBER).
                        withField(FieldType.SERIES).withField(FieldType.TYPE).withField(FieldType.CHAPTER).
                        withField(FieldType.PAGES).withField(FieldType.ADDRESS).withField(FieldType.EDITION).
                        withField(FieldType.MONTH).withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Incollection().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case MANUAL:
                mandatory.withField(FieldType.TITLE);
                optional.withField(FieldType.AUTHOR).withField(FieldType.ORGANIZATON).withField(FieldType.ADDRESS).
                        withField(FieldType.EDITION).withField(FieldType.MONTH).withField(FieldType.YEAR).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Manual().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case MASTERSTHESIS:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.SCHOOL).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.TYPE).withField(FieldType.ADDRESS).withField(FieldType.MONTH).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Masterthesis().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case PHDTHESIS:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.SCHOOL).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.TYPE).withField(FieldType.ADDRESS).withField(FieldType.MONTH).
                        withField(FieldType.NOTE).withField(FieldType.KEY);
                return new Phdthesis().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case TECHREPORT:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.INSTITUTION).
                        withField(FieldType.YEAR);
                optional.withField(FieldType.EDITOR).withField(FieldType.VOLUME).withField(FieldType.NUMBER).
                        withField(FieldType.SERIES).withField(FieldType.ADDRESS).withField(FieldType.MONTH).
                        withField(FieldType.ORGANIZATON).withField(FieldType.PUBLISHER).withField(FieldType.NOTE).
                        withField(FieldType.KEY);
                return new Techreport().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case MISC:
                optional.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.HOWPUBLISHED).
                        withField(FieldType.MONTH).withField(FieldType.YEAR).withField(FieldType.NOTE).
                        withField(FieldType.KEY);
                return new Misc().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
            case UNPUBLISHED:
                mandatory.withField(FieldType.AUTHOR).withField(FieldType.TITLE).withField(FieldType.NOTE);
                optional.withField(FieldType.MONTH).withField(FieldType.YEAR).withField(FieldType.KEY);
                return new Unpublished().
                        setMandatoryFields(mandatory.getResult()).
                        setOptionalFields(optional.getResult());
        }
        return null;
    }
}
