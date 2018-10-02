package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PiResult {

    private String nameOfInstance;
    private double pi;
    private long durationMilliSec;

    public double getPi() {
        return pi;
    }

    public void setPi(double pi) {
        this.pi = pi;
    }

    public long getDurationMilliSec() {
        return durationMilliSec;
    }

    public void setDurationMilliSec(long durationMilliSec) {
        this.durationMilliSec = durationMilliSec;
    }

    public void setNameOfInstance(String nameOfInstance) {
        this.nameOfInstance = nameOfInstance;
    }

    @Override
    public String toString() {
        return nameOfInstance + " -->  value of pi: " + pi + " | duration in milliseconds: " + durationMilliSec;
    }
}
