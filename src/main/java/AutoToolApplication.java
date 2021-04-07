import functionThread.*;
import utils.TransactionSQL;
import utils.UserSQL;

public class AutoToolApplication {
    public static void main(String[] args)
    {
        UserThread userThread = new UserThread("1");
        TransactionThread transactionThread = new TransactionThread("1");
        StatisticsThread statisticsThread = new StatisticsThread("1");
        CalculationThread calculationThread = new CalculationThread("1");
        MarketManagerThread marketManagerThread = new MarketManagerThread("1");

        Thread t1 = new Thread(userThread);
        Thread t2 = new Thread(transactionThread);
        Thread t3 = new Thread(statisticsThread);
        Thread t4 = new Thread(calculationThread);
        Thread t5 = new Thread(marketManagerThread);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
