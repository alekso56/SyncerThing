package io.github.alekso.gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author Oleg Ryaboy, based on work by Miguel Enriquez 
 */
public class RegistryInstallFinder {

    /**
     * 
     * @param location path in the registry
     * @param key registry key
     * @return registry value or null if not found
     */
    public static final String readRegistry(String location, String key){
        try {
            // Run reg query, then read output with StreamReader (internal class)
        	Process process = null;
        	if(key.isEmpty() || key.equals("(Default)")) {
        		 process = Runtime.getRuntime().exec("reg query " + 
                         '"'+ location + "\" /ve ");
        		// System.out.println("reg query " + '"'+ location + "\" /ve ");
        	}else {
             process = Runtime.getRuntime().exec("reg query " + 
                    '"'+ location + "\" /v " + key);
        	}
            StreamReader reader = new StreamReader(process.getInputStream());
            reader.start();
            process.waitFor();
            reader.join();
            String output = reader.getResult();

            // Output has the following format:
            //     (Default)    REG_SZ    "C:\Program Files (x86)\Steam\steamapps\common\VRChat\launch.bat" "C:\Program Files (x86)\Steam\steamapps\common\VRChat" "%1"

            if(output.contains("ERROR")){
                    return null;
            }
            String[] split;
            if(output.contains("\"")) {
            	split = output.split("\"");
            }else {
            	split = output.split(" ");
            }

            String result = "";
            if(key.isEmpty() || key.equals("(Default)")) {
            	result = split[3];
            }else {
            	result = split[14].replaceAll("\\s+",System.getProperty("line.separator"));
            }
           // System.out.println(result);
            // Parse out the value
            return result;
        }
        catch (Exception e) {
            return null;
        }

    }

    static class StreamReader extends Thread {
        private InputStream is;
        private StringWriter sw= new StringWriter();

        public StreamReader(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);
            }
            catch (IOException e) { 
        }
        }

        public String getResult() {
            return sw.toString();
        }
    }
}