package com.foo;

import java.lang.management.ThreadInfo;

/**
 * 死锁检测接口
 * 
 * @author foo
 * 
 */
public interface DeadlockHandler
{
    void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}
