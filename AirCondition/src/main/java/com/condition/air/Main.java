package com.condition.air;

import org.apache.commons.cli.*;

import java.sql.SQLOutput;

/**
 * Main class, which is responsible for line commend API.
 *
 * @author Dawid Majchrowski
 */

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(Option.builder("nf")
                .longOpt("noforce")
                .desc("Don't force update, use cache")
                .build());
        options.addOption(Option.builder("ss")
                .argName("stationsNames")
                .hasArgs()
                .longOpt("stations")
                .desc("Name of stations")
                .build());
        options.addOption(Option.builder("N")
                .argName("int")
                .hasArg()
                .longOpt("number")
                .desc("Number of parameters over norm, natural number from 1 to 7")
                .build());
        options.addOption(Option.builder("sn")
                .argName("stationsName")
                .hasArg()
                .longOpt("station")
                .desc("Name of station")
                .build());
        options.addOption(Option.builder("st")
                .argName("time")
                .hasArg()
                .longOpt("starttime")
                .desc("Starting time format YYYY-MM-DD HH:MM:SS")
                .build());
        options.addOption(Option.builder("et")
                .argName("time")
                .hasArg()
                .longOpt("endtime")
                .desc("Ending time format YYYY-MM-DD HH:MM:SS")
                .build());
        options.addOption(Option.builder("pm")
                .argName("parameter")
                .hasArg()
                .longOpt("parameter")
                .desc("Name of the parameter")
                .build());
        options.addOption(Option.builder("ps")
                .argName("city")
                .hasArg()
                .longOpt("printstation")
                .desc("Print station by given city")
                .build());
        options.addOption(Option.builder("pp")
                .longOpt("printparameter")
                .desc("Print all possible parameters")
                .build());
        options.addOption(Option.builder("id")
                .longOpt("index")
                .desc("Display index of given station, -sn option required")
                .build());
        options.addOption(Option.builder("pv")
                .longOpt("parametrvalue")
                .desc("Display parametr value of given station and date, -st, -sn, -pm options required ")
                .build());
        options.addOption(Option.builder("ag")
                .longOpt("average")
                .desc("Display average value from given period parameter and station, -sn, -st, -et, -pm options required ")
                .build());
        options.addOption(Option.builder("fl")
                .longOpt("fluctuation")
                .desc("Display maximum fluctuations of given stations parameter and start time, -ss, -st options required ")
                .build());
        options.addOption(Option.builder("mv")
                .longOpt("minimumvalue")
                .desc("Display parameter that has minimum value at given date, -st option required ")
                .build());
        options.addOption(Option.builder("on")
                .longOpt("overnorm")
                .desc("Display N sorted ascendent values over norm, -sn,-st,-N options required ")
                .build());
        options.addOption(Option.builder("mm")
                .longOpt("minmax")
                .desc("Display minimum and maximum information of given parametr, -pm options required ")
                .build());
        options.addOption(Option.builder("pc")
                .longOpt("printchart")
                .desc("Print chart of given station names, period and parameter, -ss, -st, -et, -pm options required ")
                .build());


        try {
            CommandLine line = parser.parse(options, args);
            AirConditionFacade airConditionFacade = new AirConditionFacade();
            if(!check(line)){
                throw new ParseException("Couln't parse");
            }
            else{
                if(line.hasOption("ag")){
                    if(!(line.hasOption("sn") && line.hasOption("st") && line.hasOption("et") && line.hasOption("pm")))
                        throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String station = line.getOptionValue("sn");
                    String startTime = line.getOptionValue("st");
                    String endTime = line.getOptionValue("et");
                    String parameter = line.getOptionValue("pm");
                    airConditionFacade.printAverageValue(parameter,station,startTime,endTime,force);
                }
                if(line.hasOption("fl")){
                    if(!(line.hasOption("ss") && line.hasOption("st")))
                        throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String[] stations = line.getOptionValues("ss");
                    String startTime = line.getOptionValue("st");
                    airConditionFacade.printMaxFluctuations(stations,startTime,force);
                }
                if(line.hasOption("id")){
                    if(!line.hasOption("sn")) throw new ParseException("Couln't parse");
                    String station = line.getOptionValue("sn");
                    airConditionFacade.printIndex(station);
                }
                if(line.hasOption("mm")){
                    if(!line.hasOption("mm")) throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String parametr = line.getOptionValue("pm");
                    airConditionFacade.findMinAndMax(parametr,force);
                }
                if(line.hasOption("mv")){
                    if(!(line.hasOption("st")))
                        throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String startTime = line.getOptionValue("st");
                    airConditionFacade.findMinParameter(startTime,force);
                }
                if(line.hasOption("on")){
                    if(!(line.hasOption("sn") && line.hasOption("N") && line.hasOption("st")))
                        throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                      force = false;
                    String station = line.getOptionValue("sn");
                    String date = line.getOptionValue("st");
                    Integer N = Integer.valueOf(line.getOptionValue("N"));
                    airConditionFacade.findNParametersOverNorm(station, date, N, force);
                }
                if(line.hasOption("pc")){
                    if(!(line.hasOption("ss") && line.hasOption("st") && line.hasOption("et") && line.hasOption("pm")))
                    {
                        throw new ParseException("Couln't parse");
                    }
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String[] stations = line.getOptionValues("ss");
                    String startTime = line.getOptionValue("st");
                    String endTime = line.getOptionValue("et");
                    String parameter = line.getOptionValue("pm");
                    airConditionFacade.printChart(parameter,stations,startTime,endTime,force);
                }
                if(line.hasOption("pv")){
                    if(!(line.hasOption("st") && line.hasOption("sn") && line.hasOption("pm")))
                        throw new ParseException("Couln't parse");
                    Boolean force = true;
                    if(line.hasOption("nf"))
                        force = false;
                    String station = line.getOptionValue("sn");
                    String startTime = line.getOptionValue("st");
                    String parameter = line.getOptionValue("pm");
                    airConditionFacade.printValueOfParameter(parameter,station,startTime,force);

                }
                if(line.hasOption("pp")){
                    airConditionFacade.printParameters();
                }
                if(line.hasOption("ps")){
                    String city = line.getOptionValue("ps");
                    airConditionFacade.printByCity(city);
                }
            }
        } catch (ParseException exp) {
            printHelp(options);
        }
    }

    private static boolean check(CommandLine line){
        Integer counter = 0;
        if(line.hasOption("ag")) counter++;
        if(line.hasOption("fl")) counter ++;
        if(line.hasOption("id")) counter ++;
        if(line.hasOption("mm")) counter ++;
        if(line.hasOption("mv")) counter ++;
        if(line.hasOption("on")) counter ++;
        if(line.hasOption("pc")) counter ++;
        if(line.hasOption("pv")) counter ++;
        if(line.hasOption("pp")) counter ++;
        if(line.hasOption("ps")) counter ++;
        return counter == 1;
    }

    private static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar AirConditionAPI [options]", options, false);
    }
}
