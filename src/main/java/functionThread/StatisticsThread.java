package functionThread;

import utils.TransactionSQL;

public class StatisticsThread implements Runnable{
    TransactionSQL sql = new TransactionSQL();
    public boolean flag = true;
    private String name;

    public StatisticsThread(String name)
    {
        this.name = name;
    }

    public StatisticsThread() {
        this("UnNamed StatisticsThread");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while(flag)
        {
            try {
                int result = sql.updateStatisticsPrice();
                if(result == 1)
                    System.out.println("Statistics Thread "+name+": Updated Statistics Price.");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!flag)
        {
            sql.closeConnection();
        }
    }
}
