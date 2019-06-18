package uk.markausten;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Detects when a process is finished and invokes the associated listeners.
 */
public class ProcessExitDetector extends Thread
{
    /**
     * The process for which we have to detect the end.
     */
    private Process process;
    /**
     * The associated listeners to be invoked at the end of the process.
     */
    private List<ProcessListener> listeners = new ArrayList<>();

    /**
     * Starts the detection for the given process
     *
     * @param process the process for which we have to detect when it is finished
     */
    ProcessExitDetector(Process process)
    {
        try
        {
            // test if the process is finished
            process.exitValue();
            throw new IllegalArgumentException("The process is already ended");
        }
        catch (IllegalThreadStateException exc)
        {
            this.process = process;
        }
    }

    /**
     * @return the process that it is watched by this detector.
     */
    public Process getProcess()
    {
        return process;
    }

    public void run()
    {
        try
        {
            // wait for the process to finish
            process.waitFor();

            // invokes the listeners
            for (ProcessListener listener : listeners)
            {
                listener.processFinished(process);
            }
        }
        catch (InterruptedException e)
        {
            LogClass.log.info(e.getMessage());
        }
    }

    /**
     * Adds a process listener.
     *
     * @param listener the listener to be added
     */
    void addProcessListener(ProcessListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a process listener.
     *
     * @param listener the listener to be removed
     */
    public void removeProcessListener(ProcessListener listener)
    {
        listeners.remove(listener);
    }
}