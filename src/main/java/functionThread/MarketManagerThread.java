package functionThread;

import utils.MarketManagerSQL;

public class MarketManagerThread implements Runnable{
    private String name;
    private MarketManagerSQL marketManagerSQL = new MarketManagerSQL();
    private boolean flag = true;
    public MarketManagerThread(String name)
    {
        this.name = name;
    }
    public MarketManagerThread()
    {
        this("Default Market Manager Thread");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int count = 10;
        while (flag)
        {
            try {
                if(count >= 10)
                {
                    marketManagerSQL.resetMarketManager();
                    count = 0;
                    System.out.println(name+" reset the Market Manager");
                }
                marketManagerSQL.autoUpdate2();
                count++;
                Thread.sleep(5000);
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
