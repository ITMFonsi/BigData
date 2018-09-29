package at.fhv.itm2018.awswebapp;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

public class PiResult implements Serializable {
    @Getter
    @Setter
    private double pi;
    @Getter @Setter private long duration;


    public PiResult(int numberOfThrows) {
        long startTime = System.nanoTime();
        pi = getPi(numberOfThrows);
        long endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
    }

    public static double getPi(int numThrows){

        int inCircle= 0;
        for(int i= 0;i < numThrows;i++){
            double randX= (Math.random() * 2) - 1;//range -1 to 1
            double randY= (Math.random() * 2) - 1;//range -1 to 1
            double dist= Math.sqrt(randX * randX + randY * randY);
            if(dist < 1){
                inCircle++;
            }
        }
        return 4.0 * inCircle / numThrows;
    }
    @Override
    public String toString() {
        return "Pi: " + pi + "  Duration in milliseconds: " + duration;
    }
}
