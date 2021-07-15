package cf.nquan.util;

public class Timer {

    private long time;

    public long lastMS = System.currentTimeMillis();

    public void reset(){
        lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset){
        if(System.currentTimeMillis()-lastMS > time){
            if(reset)
                reset();

            return true;
        }

        return false;
    }

    public long time() {
        return System.nanoTime() / 1000000L - time;
    }
    public boolean reach(final long time) {
        return time() >= time;
    }


    public void clear() {
    }
}
