import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* <h1>Binary tree traversal for defective lightbulbs</h1>

The class {@code Bulb()} is responsible to describe every objet representing a lightbulb as a boolean value. The boolean array is copied from 
{@link ReadFile#getList() getList()} method. An array for defective bulbs is initialized as a counter of non functionning bulbs. 
The following methods are executed on the main array {@code bulb[]} and is available to the client:
<ul>
    <li>{@code findDefective()}</li>
    <li>{@code printDefective()}</li>
    <li>{@code getThreadCounter()}</li>
</ul>
<p>
Internal private methods are responsible for verifying, recursively, the bulb array for any defective lightbulb.
The search uses the Divide and Conquer algorithm to efficientely check each section of the array for defective bulb.
The array is divided in two halves at each recursive call if a defective bulb is found. Otherwise the recursion is skipped with a @return void.
For each half created, a new thread is generated so algorithm can be executed concurrently. To avoid overwritting results, the {@code checkDefective()} and {@code threadCounter} are manipulated atomically.
</p>

* @author  Kevin de Oliveira
* @version 1.0
* @since   2020/07/29
*/

public class Bulb {
    private boolean[] bulbs;
    private int threadCounter;
    private List<Integer> defective;
    private final int SIZE;
    /**
     * Creates an object for a list of bulbs
     * @param list boolean array
     */
    public Bulb(boolean[] list){
        this.threadCounter = 0;
        this.SIZE = list.length;
        this.bulbs = Arrays.copyOf(list, SIZE);
        defective = new ArrayList<>();
    }
    /**
     * Calls recursive search on {@code bulb[]}
     * @return void
     */
    public void findDefective(){
        this.findDefective(0, SIZE - 1);
    }
    /**
     * Recursive search that divides and create new Thread for each section containing defective bulb.
     * The {@code threadCounter} is incremented via synchronization at each recursion level.
     * <b> Since threads are created in pairs, the total value of threads created is {@code 2 * threadCounter}</b>
     * @param low lower index of half section
     * @param high higher index of half section
     * @return void
     */
    private void findDefective(int low, int high){
        if(low >= high || !this.verifyDefective(low, high)){
            this.checkDefective(low);
            return;
        }

        int mid = (low+high)/2;

        // New thread is created for each section

        Thread tLeft = new Thread("left array"){
            @Override
            public void run(){
                findDefective(low, mid);
            }
        };
        Thread tRight = new Thread("left right"){
            @Override
            public void run(){
                findDefective(mid+1, high);               
            }
        };

        //Threads start
        tLeft.start();
        tRight.start();
        
        synchronized(this){
            this.threadCounter++; 
        }
    }
    /**
     * Check if array section contains any defective bulb
     * @param low lower index of half section
     * @param high higher index of half section
     * @return the lightbulb status (defective: true, functionning: false)
     */
    private boolean verifyDefective(int low, int high){
        boolean flag = false;

        for(int i = low ; i <= high ; i++)
            if(!bulbs[i])
                flag = true;

        return flag;
    }
    /**
     * Adds the index of a defective lightbulb into {@code defecive[]}.
     * @param item index of defective lightbulb
     */
    private synchronized void checkDefective(int item){
        if(!bulbs[item])
            defective.add(item+1);
    }
    /**
     * Prints the results
     */
    public void printDefective(){
        Logger log = Logger.getLogger("Bulb.class");



        log.log(Level.INFO, "Bulb {0} malfunction", defective);

        log.log(Level.INFO, "Number of threads created {0} ", this.threadCounter);
    }
    /**
     * Get the number of threads generated during search
     * @return total number of threads created
     */
    public int getThreadCounter(){
        //Threads executed in pair
        return threadCounter*2;
    }


}
