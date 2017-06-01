package com.road.pitaya.deadlockDetector;

import java.lang.management.ThreadInfo;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.road.util.TimeUtil;

import ch.qos.logback.classic.Logger;

/**
 * 
 * @author fei.wang
 *
 */
public class DeadlockDetectorAdapter
{
    public class DeadlockConsoleHandler implements DeadlockHandler
    {
        @Override
        public void handleDeadlock(final ThreadInfo[] deadlockedThreads)
        {
            if (deadlockedThreads == null)
                return;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TimeUtil.getDateFormat(new Date())).append(":SMS-Alarm:Deadlock detected!\n");
            for (ThreadInfo threadInfo : deadlockedThreads)
            {
                if (threadInfo == null)
                    continue;

                for (Thread thread : Thread.getAllStackTraces().keySet())
                {
                    if (thread.getId() != threadInfo.getThreadId())
                        continue;

                    stringBuilder.append(threadInfo.toString().trim()).append("\n");
                    for (StackTraceElement ste : thread.getStackTrace())
                        stringBuilder.append("\t" + ste.toString().trim()).append("\n");

                    // TODO:
                    // thread.interrupt();
                }
            }

            LOGGER.error(stringBuilder.toString());
        }
    }

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DeadlockDetectorAdapter.class);

    private static class LazyHolder
    {
        private static final DeadlockDetectorAdapter INSTANCE = new DeadlockDetectorAdapter();
    }

    public static DeadlockDetectorAdapter getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    private DeadlockDetector deadlockDetector;

    public DeadlockDetectorAdapter()
    {
        deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(), 3, TimeUnit.MINUTES);
    }

    public boolean init()
    {
        deadlockDetector.start();
        return true;
    }
}
