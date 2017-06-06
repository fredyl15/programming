package paging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class Paging {

    public static int numPageRef, refString[], pageFrame, maxPage, frames[],fault, count;
    public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static linkedQueue refQ = new linkedQueue();		//this is where the deleted items go
    public static int frameCount = 0;							//number of frames used
    public static int pageTable[][];
    

    public static linkedQueue queue = new linkedQueue();

    public Paging() throws IOException {
        Random rand = new Random();

        System.out.println("Enter number of page reference: ");
        numPageRef = Integer.parseInt(input.readLine());
        refString = new int[numPageRef];
        System.out.println("Enter the max page number: ");
        maxPage = Integer.parseInt(input.readLine());

        System.out.println("Enter number of page frames: ");
        pageFrame = Integer.parseInt(input.readLine());
        frames = new int[pageFrame];
        pageTable = new int[maxPage][5];			//tells us where each page is stored page
        //pageTable[i][0] tells us what frame the page is stored in (i is the page #)
        //pageTable[i][1] tells us if it is invalid or valid(0 is invalid; 1 is valid)
        //So if page 3 is in frame 2, pageTable[3][0] = 2 and pageTable[3][1] = 1
        //pageTable[i][2] tells us how far away the page is from being referenced again (for optimal)
        count = 1;
       
        for (int i = 0; i < numPageRef; i++) {
            int item = rand.nextInt(maxPage);
            queue.insert(item);
            refString[i] = item;
        }

        queue.display();

    }

    public static void main(String[] args) throws IOException {
        Paging p = new Paging();
        System.out.println("FIFO");
        FIFO();
        resetPageTable();
        refQ.display();
        System.out.println("Optimal");
        optimal();
       
        queue.display();
        System.out.println("Least Recently Used");
        resetPageTable();
        LRU();
        

    }

    public static void FIFO() {
        Arrays.fill(frames, -1);					//fills all the frames with the value -1
        int temp, victim;
        while (!queue.isEmpty()) {
            temp = queue.remove();					//remove from queue
            refQ.insert(temp);						//inset it into this new queue so the data is not lost
            if (pageTable[temp][1] == 0) {
                victim = frames[frameCount];		//the page that will be removed from the frame
                if (victim >= 0) {
                    pageTable[victim][1] = 0;		//set it as invalid in the page table
                }
                frames[frameCount] = temp;			//insert the page to next frame if it is not in any frame (AKA it is invalid)
                pageTable[temp][0] = frameCount;	//write which frame it is in to page table
                pageTable[temp][1] = 1;				//set it as valid
                frameCount++;						//next time, it will insert into next frame
                fault++;
            }
            if (frameCount > pageFrame - 1) //if the last frame has been just filled
            {
                frameCount = 0;		//loop back to the 1st frame
            }
            for (int i = 0; i < pageFrame; i++) {
                System.out.print(frames[i] + " ");
            }
            System.out.println("faults: " + fault);
        }
    }

    public static void optimal() {
        int farthest = -1;
        Arrays.fill(frames, -1);
        frameCount = 0;
        fault = 0;
        int temp, victim;
        for (int i = 0; i < numPageRef; i++) {
            temp = refQ.remove();
            queue.insert(temp);
            if (frameCount < pageFrame && pageTable[temp][1] == 0) {	//if there exists a free frame and the page is not in a frame
                frames[frameCount] = temp;			//add it to the free frame
                pageTable[temp][0] = frameCount;
                pageTable[temp][1] = 1;
                frameCount++;
                fault++;
            } else if (pageTable[temp][1] == 0) {
                farthest = search(i);				//search the reference string
                victim = frames[pageTable[farthest][0]];		//find the page that will be referenced latest in the queue
                if (victim >= 0) {
                    pageTable[victim][1] = 0;					//set it as invalid in the page table
                }
                frames[pageTable[farthest][0]] = temp;			//insert the page to next frame if it is not in any frame (AKA it is invalid)
                pageTable[temp][0] = pageTable[farthest][0];	//write which frame it is in to page table
                pageTable[temp][1] = 1;							//set it as valid
                fault++;
                resetDistance();			//reset the distance for each page to 0
            }
            for (int j = 0; j < pageFrame; j++) {
                System.out.print(frames[j] + " ");
            }
            System.out.println("faults: " + fault);
        }
    }
    
    public static void LRU() {
        Arrays.fill(frames, -1);
        frameCount = 0;
        fault = 0;
        int temp = -1;
        int least, victim = 0;
        for (int i = 0; i < numPageRef; i++) {
            temp = queue.remove();
            
           // refQ.insert(temp);
            
            
                if (frameCount < pageFrame && pageTable[temp][1] == 0) {	//if there exists a free frame and the page is not in a frame
                    
                    frames[frameCount] = temp;			//add it to the free frame
                    pageTable[temp][0] = frameCount;
                    pageTable[temp][1] = 1;
                    
                    frameCount++;
                     fault++;
                     for(int z = 0; z < frameCount; z++)
                     {
                         pageTable[frames[z]][4]++;
                     }
                    
                    }  
                           
                   if(pageTable[temp][1] == 1)
                       pageTable[temp][4] = 0;
                   
                   
                       
        else if(pageTable[temp][1] == 0){
                for(int k = 0; k < pageFrame; k++){
                   // System.out.println(pageTable[frames[0]][4]);
                    least = pageTable[frames[0]][4];
                    if(pageTable[frames[k]][4] >= least && frames[k] != temp){
                        victim = frames[k];
                        
                    }
                    
                
            }
                pageTable[victim][1] = 0;
                         pageTable[victim][4] = 0;
                        frames[pageTable[victim][0]] = temp;
                        pageTable[temp][1] = 1;
                        for(int z = 0; z < frameCount; z++)
                     {
                         pageTable[frames[z]][4]++;
                     }
                         if(pageTable[temp][1] == 1)
                       pageTable[temp][4] = 0;
                    }
        
                if(pageTable[temp][1] == 1)
                    pageTable[temp][4] = 0;
            for (int j = 0; j < pageFrame; j++) {
                System.out.print(frames[j] + " ");
               
            }
            System.out.println("faults: " + fault);
        }  
                
        }

    public static int search(int index) {
        int count = 1;
        int farthest = -1;
        for (int i = index; i < numPageRef; i++) {
            if (pageTable[refString[i]][1] == 1 && count < pageTable[refString[i]][2] || pageTable[refString[i]][2] == 0) {
                pageTable[refString[i]][2] = count;
                farthest = refString[i];
            }
            count++;
        }

        for (int i = 0; i < pageFrame; i++) {	 //if a page that is in a frame will not be referened anymore, set that as the victim
            if (pageTable[frames[i]][2] == 0) {
                farthest = frames[i];
            }
        }

        return farthest;
    }

    public static void resetPageTable() {	       //resets all pages to 0 (invalid)
        for (int i = 0; i < maxPage; i++) {
            pageTable[i][1] = 0;
        }
    }

    public static void resetDistance() {		//resets distance to for pages to 0
        for (int i = 0; i < maxPage; i++) {
            pageTable[i][2] = 0;
        }
    }

}
