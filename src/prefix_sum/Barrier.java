/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prefix_sum;

/**
 *
 * @author AliMohammed
 */
//A barrier is a type of synchronization method
//A barrier for a group of threads or processes in the source code means any thread/process must stop at this point 
//and cannot proceed until all other threads/processes reach this barrier.[1]

/*
 * Given object , no more than one thread can execute a synchronized
   method of object  at any time.
 */
// handle thread sync
public class Barrier {

    final void mywait() throws Exception {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new Exception("InterruptedException" + e);
        }
    }
    private int totalNoThreads;
    private int noThreadsWaiting;

    Barrier(int n) {
        totalNoThreads = n;
        noThreadsWaiting = 0;
    }
    // made sure that only one theards can access resourcse at a point of time

    synchronized void Synchronize() throws Exception {
        noThreadsWaiting++;
        if (noThreadsWaiting < totalNoThreads) {
            mywait();
        } else {
            // wake up all threads that are wating on object  by calling wait
            notifyAll();
            noThreadsWaiting = 0;
        }
    }
}
