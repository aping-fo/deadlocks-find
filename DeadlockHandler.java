package com.road.pitaya.deadlockDetector;

import java.lang.management.ThreadInfo;

/**
 * 死锁检测接口
 * 
 * @author fei.wang
 * 
 */
public interface DeadlockHandler
{
    void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}
