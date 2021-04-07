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
        int count = 0;
        while (flag)
        {
            try {
                marketManagerSQL.autoUpdate();
                count++;
                if(count >= 100)
                {
                    marketManagerSQL.resetMarketManager();
                    count = 0;
                    System.out.println(name+" reset the Market Manager");
                }
                Thread.sleep(2000);
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
