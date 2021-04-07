package functionThread;

import calculation.Calculation;
import utils.TransactionSQL;

public class CalculationThread implements Runnable{
    Calculation sql = new Calculation();
    public boolean flag = true;
    private String name;
    public CalculationThread(String name)
    {
        this.name = name;
    }

    public CalculationThread() {
        this("UnNamed CalculationThread");
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
                Thread.sleep(100);
                sql.updatePrice();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Calculation Thread "+name+": Failed!");
            }
        }
        if(!flag)
        {
            sql.closeConnection();
            System.out.println("Calculation Thread "+name+": Terminated!");
        }
    }
}
