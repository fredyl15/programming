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
public class JavaThreads implements Runnable {
  
    // We are creating a new class that implements the Runnable interface,
    // so we need to override and implement it's only method, run().
    @Override
    public void run(){
        
         // We are creating a simple loop which will run and allow us to take
        // a look into how the different threads run.
        for(int i=0 ; i<5; i++){
            
             // Thread.currentThread().getName() is used to get the name of the 
            // currently running thread. We can provide a name, if we create
            // the thread ourselves, else it will be given one from the JVM.
            System.out.print(Thread.currentThread().getName() + " runs " + i +"\n");
        }
    }
    
}
