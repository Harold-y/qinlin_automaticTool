package test;

import calculation.Calculation;
import functionThread.*;
import utils.MarketManagerSQL;
import utils.PriceSQL;
import utils.TransactionSQL;
import utils.UserSQL;

public class Test1 {
    public static void main(String[] args)
    {
        /*UserThread userThread = new UserThread();
        userThread.run();*/
        /*TransactionThread transactionThread = new TransactionThread();
        transactionThread.run();*/
        /*PriceSQL priceSQL = new PriceSQL();
        System.out.println(priceSQL.selectPriceByCoinId(1));
        priceSQL.closeConnection();*/
        /*UserSQL userSQL = new UserSQL();
        System.out.println(userSQL.isAutoUser(11));
        userSQL.closeConnection();*/
        /*Calculation calculation = new Calculation();
        calculation.updatePrice();*/
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

        /*t1.start();
        t2.start();
        t3.start();
        t4.start();*/
        t5.start();
    }
}
