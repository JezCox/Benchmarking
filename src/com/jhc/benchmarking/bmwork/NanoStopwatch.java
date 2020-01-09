package com.jhc.benchmarking.bmwork;

class NanoStopwatch {
    
    private long timeAtStart = 0;
    
    public void Start() {
      timeAtStart = System.nanoTime();
    }
    
    public long Stop() {
        return System.nanoTime() - timeAtStart;
    }
    
    public void Reset() {
        timeAtStart = 0;
    }
    
}
