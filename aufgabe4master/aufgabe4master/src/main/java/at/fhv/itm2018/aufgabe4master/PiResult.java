package at.fhv.itm2018.aufgabe4master;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PiResult {
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

    @Override
    public String toString() {
        return "PiResult{" +
                "pi='" + pi + '\'' +
                ", duration=" + durationMilliSec +
                '}';
    }
}
