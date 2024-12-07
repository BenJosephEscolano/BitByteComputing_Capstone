package Util;

public class Timer {
    private double endTime;
    private double currTime;

    public Timer(float endTime){
        this.endTime = endTime;
        this.currTime = 0.00;
    }

    public Timer(float endTime, float currTime){
        this.endTime = endTime;
        this.currTime = currTime;
    }

    public boolean isTime(double dt){
        addTime(dt);
        if (currTime > endTime){
            return true;
        }
        return false;
    }

    public void addTime(double dt){
        currTime += dt;
    }

    public void setEndTime(double endTime){
        this.endTime = endTime;
    }

    public void resetTime(){
        currTime = 0.0;
    }

    public String toString(){
        return currTime + "";
    }
}
