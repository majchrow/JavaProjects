package com.condition.air;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AirConditionFacadeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static AirConditionFacade airConditionFacade;
    public static String expected = "xd";

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @BeforeAll
    public static void fill(){
        airConditionFacade = new AirConditionFacade();
    }

    @Test
    public void shouldPrintCities() {
        String city = "Wrocław";
        airConditionFacade.printByCity(city);
        assertEquals("Wrocław - Wiśniowa\n" +
                "Wrocław - Bartnicza\n" +
                "Wrocław - Korzeniowskiego\n", outContent.toString());
    }

    @Test
    public void shouldPrintParameters() {
        airConditionFacade.printParameters();
        assertEquals("NO2\n" +
                "O3\n" +
                "PM2.5\n" +
                "SO2\n" +
                "PM10\n" +
                "CO\n" +
                "C6H6\n", outContent.toString());
    }

    @Test
    public void shouldThrowException() {;
        String station = "Kraków, nie ma";
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.printIndex(station));
    }

    @Test
    public void shouldThrowException2() {
        String station = "Kraków, ul. Bujaka";
        String startdate = "wrong date";
        String parameter = "PM10";
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.printValueOfParameter(parameter,station,startdate,false));
    }

    @Test
    public void shouldThrowException3() {
        String station = "Kraków, ul. Bujaka";
        String startdate = "wrong date";
        String end = "wrong date";
        String parameter = "PM10";
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.printAverageValue(parameter,station,startdate,end,false));
    }

    @Test
    public void shouldThrowException4() {
        String[] stations = {"Kraków, ul. Bujaka", "Kraków, Aleja Krasińskiego", "Kraków, os. Piastów", "Kraków, ul. Dietla", "Wrong one"};
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.printMaxFluctuations(stations,"wrong",false));
    }

    @Test
    public void shouldBeLessThan1Sec() {
        long startTime = System.nanoTime();
        airConditionFacade.findMinParameter("2019-01-21 00:00:00",false);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        assertTrue(duration<1e9);
    }

    @Test
    public void shouldThrowException5() {
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.findNParametersOverNorm(
                "Kraków, ul. Bujaka","2019-01-13 22:00:00",0,false));
    }

    @Test
    public void shouldThrowException6() {
        assertThrows(IllegalArgumentException.class, () -> airConditionFacade.findMinAndMax("PM111",false));
    }


}
