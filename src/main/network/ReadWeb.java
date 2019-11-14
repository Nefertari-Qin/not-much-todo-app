package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadWeb {
    private BufferedReader br = null;
    private String theURL;

    public ReadWeb() {
        theURL = "https://www.students.cs.ubc.ca/~cs-210/2018w1/welcomemsg.html";
    }

    public void printWebInfo() throws IOException {
        try {
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
