package com.condition.air;

/**
 * The interface indicates, that Visitor can visit Viistable.
 *
 * @author Dawid Majchrowski
 */
public interface Visitable {
    void accept(Visitor visitor);
}
