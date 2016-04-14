package com.chinacloudly.loganalyser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.chinacloudly.loganalyser.utils.Utils;

/**
 * grep
 * @author hongtu_zang
 *
 */
public class Grep {
    private static final Logger _logger = Logger.getLogger(Grep.class);
    
    private boolean scanDir = false;
    private String dir;
    private String targetParam;
    private final List<String> files = new ArrayList<String>();
    
    private static long lineCount = 0;
    private static long totalLineCount = 0;
    
    public Grep(String[] args) {
        if(args[1].equals("-R")) {
            scanDir = true;
            dir = args[1];
            targetParam = args[2];
            for(int i = 3; i < args.length; i++) {
                files.add(args[i]);
            }
        } else {
            targetParam = args[1];
            for(int i = 2; i < args.length; i++) {
                files.add(args[i]);
            }
        }
    }
    
    public void grep_r() {
        List<String> listFiles = Utils.traverseDir(dir);
        for(String file : listFiles) {
            grep_one_file(file);
        }
    }

    private void grep_one_file(String file) {
        FileReader reader = null;
        BufferedReader br = null;
        try {
            reader = new FileReader(file);
            br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                String result = analyseline(line);
                if(result != null) {
                    _logger.info(new StringBuilder(file).append(":").append(result));
                }
                totalLineCount ++;
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            _logger.error("File not found, file: " + file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            _logger.info("File total line number: " + totalLineCount);
            _logger.info("File grep result line number: " + lineCount);
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    _logger.error("Close BufferedReader failed due to " + e.getMessage());
                }
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    _logger.error("Close FileReader failed due to " + e.getMessage());
                }
            }
        }
    }
    
    private String analyseline(String line) {
        if(line.contains(targetParam)) {
            lineCount ++;
            return line;
        }
        return null;
    }

    public void execute() {
        if(scanDir) {
            grep_r();
        } else {
            grep_files();
        }
        System.exit(0);
    }

    private void grep_files() {
        for(String file : files) {
            grep_one_file(file);
        }
    }
}
