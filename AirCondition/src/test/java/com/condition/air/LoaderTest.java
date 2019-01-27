package com.condition.air;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

    private static Loader loader;
    @BeforeAll
    public static void fill(){
        loader = new Loader();
    }

    @Test
    public void shouldThrow(){
        assertThrows(IllegalArgumentException.class,
                () -> loader.Update("WRONG URL,","WORNGPATH","DDD",loader.methodGet));
    }
}
