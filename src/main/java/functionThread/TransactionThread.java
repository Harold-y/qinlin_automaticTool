package functionThread;

import utils.TransactionSQL;

public class TransactionThread implements Runnable{
    TransactionSQL sql = new TransactionSQL();
    public boolean flag = true;
    private String name;
    public TransactionThread(String name)
    {
        this.name = name;
    }

    public TransactionThread() {
        this("UnNamed TransactionThread");
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
                int result = sql.insertRandomTransaction();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Transaction Thread "+name+": Failed!");
            }
        }
        if(!flag)
        {
            sql.closeConnection();
            System.out.println("Transaction Thread "+name+": Terminated!");
        }
    }
}
