package parser;

/**
 * The interface responsible for action during visiting concrete Visitable Records.
 *
 * @author Dawid Majchrowski
 */

interface Visitor {
    void visit(Record record);
}
