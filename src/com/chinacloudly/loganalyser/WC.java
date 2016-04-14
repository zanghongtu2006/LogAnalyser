package com.chinacloudly.loganalyser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * word count
 * 
 * @author hongtu_zang
 *
 */
public class WC {
    private static final Logger _logger = Logger.getLogger(WC.class);
    
    private String param;
    private final List<String> files = new ArrayList<String>();
    
    public WC(String[] args) {
        if(args[1].equals("-l")) {
            param = args[2];
            for(int i = 3; i < args.length; i++) {
                files.add(args[i]);
            }
        } else {
            param = args[1];
            for(int i = 2; i < args.length; i++) {
                files.add(args[i]);
            }
        }
    }
    
    private long calcTotalBytes() {
       return 0; 
    }
    
    private void calcTotalLines() {
        long totalCount = 0;
        for(String file : files) {
            long count = countOneFile(file);
            totalCount += count;
            _logger.info(new StringBuilder(file).append(": ").append(count));
        }
        _logger.info(new StringBuilder("Total Line Count: " + totalCount));
    }
    
    private long countOneFile(String file) {
        RandomAccessFile rf = null;
        long count = 0;
        try {
            rf = new RandomAccessFile(file, "r");
            String line = rf.readLine();
            while(line != null) {
                count ++;
                line = rf.readLine();
            }
        } catch (FileNotFoundException e) {
            _logger.error("File not found, file: " + file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(rf != null) {
                try {
                    rf.close();
                } catch (IOException e) {
                    _logger.error("Close RandomAccessFile failed due to " + e.getMessage());
                }
            }
            if(count != 0) {
                return count;
            }
        }
        return 0;
    }

    public void execute() {
        
    }

}
