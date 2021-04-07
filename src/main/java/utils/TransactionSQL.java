package utils;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class TransactionSQL {
    Connection connection = new MySQLUtils().getConnection();
    Statement statement = null;
    UserSQL userSQL = new UserSQL();
    PriceSQL priceSQL = new PriceSQL();
    public int insertRandomTransaction()
    {
        String sql = "insert into transaction (buyerid, sellerid, coinid, amount, transaction_time, total_charge, price) values (?, ?, ?, ?, ?, ?, ?)";

        int userAmount = userSQL.selectUserAmount();
        Random random = new Random();
        int buyerId = random.nextInt(userAmount);
        boolean isRobot = false;
        while (!isRobot)
        {
            buyerId = random.nextInt(userAmount);
            isRobot = userSQL.isAutoUser(buyerId);
        }
        isRobot = false;
        int sellerId = random.nextInt(userAmount);
        while (!isRobot)
        {
            sellerId = random.nextInt(userAmount);
            isRobot = userSQL.isAutoUser(sellerId);
        }
        int coinAmount = selectCoinAmountHelper();
        int coinId = random.nextInt(coinAmount) + 1;

        double amount = random.nextInt(10000);
        amount += random.nextDouble();
        Date transactionTime = new Date();
        double price = (double) priceSQL.selectPriceByCoinId(coinId).get("price");
        boolean isMinus = random.nextBoolean();
        if(isMinus)
        {
            price-=(random.nextInt(5) * 0.01 * price);
        }else
        {
            price+=(random.nextInt(5) * 0.01 * price);
        }
        double highestPrice = selectHighestPriceFromStatistics(coinId);
        if(highestPrice < price)
            updateHighestPrice(coinId, price);
        double totalCharge = price * amount;
        updateTransactionAmount(coinId, amount);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, buyerId);
            preparedStatement.setObject(2, sellerId);
            preparedStatement.setObject(3, coinId);
            preparedStatement.setObject(4, amount);
            preparedStatement.setObject(5, transactionTime);
            preparedStatement.setObject(6, totalCharge);
            preparedStatement.setObject(7, price);
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    public int selectCoinAmountHelper()
    {
        String sql = "select count(*) from coin";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet set = ps.executeQuery(sql);
            int count = 0;
            while (set.next())
            {
                count = set.getInt("count(*)");
            }
            ps.close();
            return count;
        } catch (Exception e)
        {
            return -1;
        }
    }

    public double selectHighestPriceFromStatistics(int CoinId)
    {
        Date date = new Date();
        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
        String formatDate = formatYYYY.format(date);
        String sql = "select highestPrice from statistics where coinid = "+CoinId+" and stat_time = '"+formatDate+"'";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet set = ps.executeQuery(sql);
            double highestPrice = 0;
            int count = 0;
            while (set.next())
            {
                highestPrice = set.getDouble("highestPrice");
                count++;
            }
            if(count == 0)
                createDailyStatistics();
            ps.close();
            return highestPrice;
        } catch (Exception e)
        {
            return -1;
        }
    }

    public void createDailyStatistics()
    {
        int numCoin = selectCoinAmountHelper();
        for(int i=1;i<=numCoin;i++)
        {
            try {
                Date date = new Date();
                SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
                String formatDate = formatYYYY.format(date);
                String sql = "insert into statistics (coinid, stat_time, highestPrice, tradeAmount, price) values (?, ?, ?, ?, ?) ";
                PreparedStatement ps = null;
                ps = connection.prepareStatement(sql);
                ps.setObject(1, i);
                ps.setObject(2, formatDate);
                ps.setObject(3, 0.00);
                ps.setObject(4, 0.00);
                ps.setObject(5, 0.00);
                int result = ps.executeUpdate();
                ps.close();
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    public int updateStatisticsPrice()
    {
        int numCoin = selectCoinAmountHelper();
        int result = -1;
        for(int i=1;i<=numCoin;i++)
        {
            try {
                Date endDate = new Date(); //取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
                endDate=calendar.getTime(); //这个时间就是日期往后推一天的结果
                Date beginDate = new Date();
                SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");

                String formatDateBegin = formatYYYY.format(beginDate);
                String formatDateEnd = formatYYYY.format(endDate);
                String sql = "select price from transaction where coinid = "+i+" and transaction_time > '"+formatDateBegin +"' and transaction_time < '"+formatDateEnd+"'";
                PreparedStatement ps = null;
                ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ArrayList<Double> priceList = new ArrayList<>();
                double sumPrice = 0.00;
                while (rs.next())
                {
                    double rsPrice = rs.getDouble("price");
                    priceList.add(rsPrice);
                    sumPrice += rsPrice;
                }
                if(sumPrice == 0.00)
                {
                    continue;
                }
                double averagePrice = sumPrice / priceList.size();
                String formatPrice = new DecimalFormat("0.00").format(averagePrice);
                String  sql2 = "update statistics set price = "+formatPrice+" where coinid = "+i+" and stat_time = '"+formatDateBegin+"'";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                result = ps2.executeUpdate();
                ps.close();
                ps2.close();
            } catch (Exception e)
            {
                System.out.println(e);
            }
        }
        return result;
    }

    public int updateHighestPrice(int CoinId, double highestPrice)
    {
        Date date = new Date();
        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
        String formatDate = formatYYYY.format(date);
        String sql = "update statistics set highestPrice = "+highestPrice+" where coinid = "+CoinId+" and stat_time = '"+formatDate+"'";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (Exception e)
        {
            return -1;
        }
    }

    public int updateTransactionAmount(int CoinId, double adder)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Date date = new Date();
        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
        String formatDate = formatYYYY.format(date);
        String sql = "update statistics set tradeAmount = tradeAmount + "+df.format(adder)+" where coinid = "+CoinId+" and stat_time = '"+formatDate+"'";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            ps.close();
            return result;
        } catch (Exception e)
        {
            return -1;
        }
    }

    public void closeConnection()
    {
        try {
            connection.close();
            userSQL.closeConnection();
            priceSQL.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
