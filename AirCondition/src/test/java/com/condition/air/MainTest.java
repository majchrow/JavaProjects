package com.condition.air;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private String expected = "usage: java -jar AirConditionAPI [options]\n" +
            " -ag,--average                    Display average value from given period\n" +
            "                                  parameter and station, -sn, -st, -et,\n" +
            "                                  -pm options required\n" +
            " -et,--endtime <time>             Ending time format YYYY-MM-DD HH:MM:SS\n" +
            " -fl,--fluctuation                Display maximum fluctuations of given\n" +
            "                                  stations parameter and start time, -ss,\n" +
            "                                  -st options required\n" +
            " -id,--index                      Display index of given station, -sn\n" +
            "                                  option required\n" +
            " -mm,--minmax                     Display minimum and maximum information\n" +
            "                                  of given parametr, -pm options required\n" +
            " -mv,--minimumvalue               Display parameter that has minimum value\n" +
            "                                  at given date, -st option required\n" +
            " -N,--number <int>                Number of parameters over norm, natural\n" +
            "                                  number from 1 to 7\n" +
            " -nf,--noforce                    Don't force update, use cache\n" +
            " -on,--overnorm                   Display N sorted ascendent values over\n" +
            "                                  norm, -sn,-st,-N options required\n" +
            " -pc,--printchart                 Print chart of given station names,\n" +
            "                                  period and parameter, -ss, -st, -et, -pm\n" +
            "                                  options required\n" +
            " -pm,--parameter <parameter>      Name of the parameter\n" +
            " -pp,--printparameter             Print all possible parameters\n" +
            " -ps,--printstation <city>        Print station by given city\n" +
            " -pv,--parametrvalue              Display parametr value of given station\n" +
            "                                  and date, -st, -sn, -pm options required\n" +
            " -sn,--station <stationsName>     Name of station\n" +
            " -ss,--stations <stationsNames>   Name of stations\n" +
            " -st,--starttime <time>           Starting time format YYYY-MM-DD HH:MM:SS\n";

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldPrintHelp1() {
        String[] args = {""};
        Main.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void shouldPrintHelp2() {
        String[] args = {"-ssda"};
        Main.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void shouldPrintHelp3() {
        String[] args = {"-d", "-c"};
        Main.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void shouldPrintHelp4() {
        String[] args = {"-mm", "-pc"};
        Main.main(args);
        assertEquals(expected, outContent.toString());
    }

    @Test
    public void shouldPrintHelpandThrowException1() {
        String[] args = {"-ps", "niema"};
        assertThrows(IllegalArgumentException.class, () -> Main.main(args));
        assertEquals("No station for city niema found\n", outContent.toString());
    }

    @Test
    public void shoudlntprintHelp() {
        String[] args = {"-ps", "Kraków"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shouldPrintHelp0() {
        String[] args = {"-pp"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp1() {
        String[] args = {"-id", "-sn", "Kraków, ul. Bujaka"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp2() {
        String[] args = {"-pv", "-sn", "Kraków, ul. Bujaka", "-st", "2019-01-13 00:00:00", "-pm", "PM10", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp3() {
        String[] args = {"-ag", "-sn", "Kraków, ul. Bujaka", "-st", "2019-01-13 00:00:00", "-et", "2019-01-13 13:00:00", "-pm", "PM10", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp4() {
        String[] args = {"-fl", "-ss", "Kraków, ul. Bujaka", "Kraków, Aleja Krasińskiego", "Kraków, os. Piastów", "Kraków, ul. Dietla", "-st", "2019-01-13 00:00:00", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp5() {
        String[] args = {"-mv", "-st", "2019-01-13 00:00:00", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp6() {
        String[] args = {"-on", "-sn", "Kraków, ul. Bujaka", "-st", "2019-01-13 22:00:00", "-N", "2", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp7() {
        String[] args = {"-mm", "-pm", "PM10", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

    @Test
    public void shoudlntPrintHelp8() {
        String[] args = {"-pc", "-ss", "Kraków, ul. Bujaka", "Kraków, Aleja Krasińskiego", "Kraków, os. Piastów", "Kraków, ul. Dietla",
                "-st", "2019-01-13 00:00:00", "-et", "2019-01-13 13:00:00", "-pm", "PM10", "-nf"};
        Main.main(args);
        assertNotEquals(expected, outContent.toString());
    }

}
