package calculation;

import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculation {

    TransactionSQL transactionSQL = new TransactionSQL();
    PriceManagerSQL priceManagerSQL = new PriceManagerSQL();
    MarketManagerSQL marketManagerSQL = new MarketManagerSQL();
    PriceSQL priceSQL = new PriceSQL();
    Connection connection = new MySQLUtils().getConnection();

    public void updatePrice()
    {
        int numCoin = transactionSQL.selectCoinAmountHelper();
        for(int i=1;i<=numCoin;i++)
        {
            try {
                Date beginDate = new Date();
                SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY-MM-dd");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatDateBegin = formatYYYY.format(beginDate);
                String formatDateBegin2 = sdf.format(beginDate);
                String sql = "select price from statistics where coinid = "+i+" and stat_time = '"+formatDateBegin +"'";

                PreparedStatement ps = null;
                ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                double calculation = 0.00;
                double statisticsPrice = 0.00;
                Random ran2 = new Random();
                ran2.setSeed(i+System.currentTimeMillis());
                while (rs.next())
                {
                    statisticsPrice = rs.getDouble("price");
                }
                LinkedHashMap priceMap = priceSQL.selectPriceByCoinId(i);
                double currentPrice = (double) priceMap.get("price");
                if(statisticsPrice > 0)
                {
                    calculation = (currentPrice + statisticsPrice ) / 2;
                }else
                {
                    calculation = currentPrice;
                }
                LinkedHashMap priceManager = priceManagerSQL.selectManager(i);
                LinkedHashMap marketManager = marketManagerSQL.selectManager();
                double marketEht = 1.00;
                double marketOpt = 1.00;
                double marketExt = 1.00;
                double priceEht = 1.00;
                double priceOpt = 1.00;
                double priceAss = -1;
                double priceFloor = -1;
                double priceCeiling = -1;
                Date timeRange = null;
                if(marketManager.get("enthusiasmRate")!=null)
                {
                    marketEht = (double)marketManager.get("enthusiasmRate");
                }
                if(marketManager.get("optimismRate")!=null)
                {
                    marketOpt = (double)marketManager.get("optimismRate");
                }
                if(marketManager.get("extremeRate")!=null)
                {
                    marketExt = (double)marketManager.get("extremeRate");
                }

                if(priceManager.get("enthusiasmRate")!=null)
                {
                    priceEht = (double)priceManager.get("enthusiasmRate");
                }
                if(priceManager.get("optimismRate")!=null)
                {
                    priceOpt = (double)priceManager.get("optimismRate");
                }
                if(priceManager.get("assumeLevel")!=null)
                {
                    priceAss = (double)priceManager.get("assumeLevel");
                }
                if(priceManager.get("timeRange")!=null)
                {
                    timeRange = (Date)priceManager.get("timeRange");
                }
                if(priceManager.get("pricefloor")!=null)
                {
                    priceFloor = (double)priceManager.get("pricefloor");
                }
                if(priceManager.get("priceceiling")!=null)
                {
                    priceCeiling = (double)priceManager.get("priceceiling");
                }
                Random random = new Random();
                String[] ranDate = formatDateBegin.split("-");
                String dateRandom = "";
                for(String a : ranDate)
                    dateRandom+=a;
                long l1 = Integer.parseInt(dateRandom);
                random.setSeed(l1 + i);
                if(marketEht >= 0)
                {
                    double range = marketEht - 1.00;
                    calculation += range * ran2.nextInt(10) * 0.0001 * calculation;
                }
                if(marketOpt >= 0)
                {
                    double range = marketOpt - 1.00;
                    calculation += range * ran2.nextInt(10) * 0.0001 * calculation;
                }
                if(marketExt >= 0)
                {
                    double range = marketExt - 1.00;
                    calculation += range * ran2.nextInt(10) * 0.0001 * calculation;
                }
                if(priceEht >= 0)
                {
                    double range = priceEht - 1.00;
                    calculation += range * ran2.nextInt(10) * 0.002 * calculation;
                }
                if(priceOpt >= 0)
                {
                    double range = priceOpt - 1.00;
                    calculation += range * ran2.nextInt(10) * 0.004 * calculation;
                }
                if(timeRange!=null)
                {
                    LocalDate localDate = LocalDate.now();
                    LocalDate parse = LocalDate.parse(timeRange.toString());

                    Period between = Period.between(parse,localDate);
                    Pattern pattern = Pattern.compile("(\\d*)");
                    Matcher matcher = pattern.matcher(between.toString());
                    int betweenDays = 0;
                    while (matcher.find())
                    {
                        if(!matcher.group().equals("\n") && !matcher.group().equals(""))
                        {
                            betweenDays = Integer.parseInt(matcher.group());
                        }
                    }
                    if(priceAss!=-1)
                    {
                        double toBeChange = priceAss - currentPrice;
                        double step = toBeChange / betweenDays;
                        if(betweenDays * step > toBeChange)
                        {

                        } if(betweenDays * step < toBeChange)
                        {
                            calculation += step;
                        }

                    }
                }
                if(priceFloor != -1 && priceFloor != 0)
                {
                    if(calculation < priceFloor)
                        calculation = priceFloor;
                }
                if(priceCeiling != -1 && priceFloor != 0)
                {
                    if(calculation > priceCeiling)
                        calculation = priceCeiling;
                }
                int ranIncr = random.nextInt(100);
                if(ranIncr < 50)
                {
                    calculation = calculation - (calculation * ranIncr * 0.00002);
                }else
                {
                    calculation = calculation + (calculation * ranIncr * 0.00002);
                }
                if(calculation <= 0)
                {
                    calculation = 0.01;
                }
                String formatPrice = new DecimalFormat("0.00").format(calculation);
                String  sql2 = "update price set price_usd = "+formatPrice+", timeupdated = '"+formatDateBegin2+"' where coinid = "+i;
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.executeUpdate();
                ps.close();
                ps2.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection()
    {
        try {
            connection.close();
            transactionSQL.closeConnection();
            priceManagerSQL.closeConnection();
            marketManagerSQL.closeConnection();
            priceSQL.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
