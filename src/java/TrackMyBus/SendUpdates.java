package TrackMyBus;

import java.text.*;
import java.util.*;
import java.util.logging.*;


final class SendUpdates extends Thread {

    static final long INTERVAL = 5000;
    final Random rand = new Random();
    final DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);


    private double latitude;
    private double longitude;
    private String js;
    int i = 0;
    /**
     * Periodically updates stock info and notifies servlet threads.
     */
    @Override
    public void run() {
        while (!Thread.interrupted()) {
           
            synchronized (this) {
              getUpdates coor = new getUpdates(i);
              double [] array =coor.getData();
                latitude = array[0];
                longitude = array[1];
                //System.out.println( array[0]+"  "+array[1]);
                js="{\"latitude\":"+latitude+", \"longitude\":"+longitude+"} ";
               
                Logger.getGlobal().log(Level.INFO, this.toString());
                notifyAll();
            }
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                return;
            }
          
            if(i!=11)i++;
        }
    }
    
    @Override
    public String toString(){
        return js;
    }
}
