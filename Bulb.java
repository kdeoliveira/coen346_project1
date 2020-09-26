import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Bulb {
    private int[] bulbs;
    private int threadCounter;
    private List<Integer> defective;
    private final int SIZE;
    
    public Bulb(List<Integer> list){
        this.threadCounter = 0;
        this.SIZE = list.remove(0);
        this.bulbs = new int[this.SIZE];

        defective = new ArrayList<>();
        for(int i = 0 ; i < SIZE ; i++){
            this.bulbs[i] = list.get(i);

        }
    }

    public boolean isValid(){
        if(bulbs.length == 0)
            return false;
        
        boolean flag = false;

        for(int x : this.bulbs){
            flag = (x == 1) || (x == 0);
        }

        return !flag;
    }

    public void findDefective(){
        this.findDefective(0, SIZE - 1);
    }

    private void findDefective(int low, int high){
        if(low >= high || !this.verifyDefective(low, high)){
            this.checkDefective(low);
            return;
        }

        int mid = (low+high)/2;

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

        tLeft.start();
        tRight.start();
        
        synchronized(this){
            this.threadCounter++; 
        }
    }

    private boolean verifyDefective(int low, int high){
        boolean flag = false;

        for(int i = low ; i <= high ; i++)
            if(bulbs[i] == 0)
                flag = true;

        return flag;
    }

    private synchronized void checkDefective(int item){
        if(bulbs[item] == 0)
            defective.add(item+1);
    }

    public void printDefective(){
        Logger log = Logger.getLogger("Bulb.class");



        log.log(Level.INFO, "Bulb {0} malfunction", defective);

        log.log(Level.INFO, "Number of threads created {0} ", this.threadCounter);
    }

    public int getThreadCounter(){
        //Threads executed in pair
        return threadCounter*2;
    }


}
