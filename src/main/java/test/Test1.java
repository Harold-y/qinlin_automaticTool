package test;

import functionThread.MarketManagerThread;

import java.io.File;

public class Test1 {

    public static void main(String[] w)
    {
        MarketManagerThread thread1 = new MarketManagerThread("1");
        Thread t1 = new Thread(thread1);
        t1.start();
    }
}
