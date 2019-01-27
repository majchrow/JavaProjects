package com.condition.air;


/**
 * The interface responsible for action during visiting concrete Visitable Records.
 *
 * @author Dawid Majchrowski
 */


public interface Visitor {
    void visit(ParamValues paramValues);
}
