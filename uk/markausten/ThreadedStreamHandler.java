package uk.markausten;

import javax.swing.*;
import java.io.*;

class ThreadedStreamHandler extends Thread
{
    private InputStream inputStream;
    private MainPanel ui;
    private volatile String line;

    ThreadedStreamHandler(InputStream inputStream, MainPanel ui)
    {
        this.inputStream = inputStream;
        this.ui = ui;
    }

    public void run()
    {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
        {

            while ((line = bufferedReader.readLine()) != null)
            {
                if (line != null)
                {
                    ui.queueString(line);
                }
            }
        }
        catch (Throwable ioe)
        {
            ioe.printStackTrace();
        }
    }

}