package uk.markausten;

import java.util.EventListener;

public interface ProcessListener extends EventListener
{
    void processFinished(Process process);
}