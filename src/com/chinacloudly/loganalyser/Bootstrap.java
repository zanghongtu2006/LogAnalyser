package com.chinacloudly.loganalyser;

/**
 * start class
 * 
 * @author hongtu_zang
 *
 */
public class Bootstrap {

    public static void main(String[] args) {
        String command = args[0];
        if(command.equals("wc")) {
            WC wc = new WC(args);
        } else if(command.equals("grep")) { 
            Grep grep = new Grep(args);
            grep.execute();
        }
    }

    private static void printUsage() {
        System.out.println("Log Analyser Usage:");
        System.out.println("    java -jar log_anlyser.jar <Command> <Params>");
        System.out.println("    example:");
        System.out.println("       java -jar log_anlyser.jar c");
    }

}
