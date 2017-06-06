/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javathreads;

/**
 *
 * @author fredylezama
 */
public class Driver {
    
    public static void main(String[] args) throws InterruptedException{
        System.out.println("Executing program...");
        
        // Create a new instance of our class that implements the Runnable interface.
        // This class can be provided as an argument to a Thread instance.
        JavaThreads r = new JavaThreads();
        
       // Create a new Thread instance, provide the task that we want to run
        // (by providing the Runnable as an argument) and give the thread a name.
        // Now we can use Thread.start() to run it!
        Thread t1 = new Thread(r, "Thread 1");
        t1.start();
        t1.join();
        
        // We are creating a second thread. Take notice that we are
        // providing the same Runnable, so this thread will run the same task
        // as the first one.
        Thread t2 = new Thread(r, "Thread 2");
        t2.start();
        t2.join();
        
        
    }
    
}
