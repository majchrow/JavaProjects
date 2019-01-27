package parser;

/**
 * The interface indicates, that Visitor can visit Viistable.
 *
 * @author Dawid Majchrowski
 */

interface Visitable {
    void accept(Visitor visitor);
}
