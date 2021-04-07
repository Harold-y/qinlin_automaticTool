package utils;

import java.sql.*;
import java.util.Date;
import java.util.LinkedHashMap;

public class PriceSQL {
    Connection connection = new MySQLUtils().getConnection();
    Statement statement = null;

    public LinkedHashMap selectPriceByCoinId(int coinId)
    {
        String sql = "select priceid, price_usd, timeupdated from price where coinid = " + coinId;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery(sql);
            LinkedHashMap map = new LinkedHashMap();
            while (rs.next())
            {
                int priceId = rs.getInt("priceid");
                double price = rs.getDouble("price_usd");
                Date timeupdated = rs.getDate("timeupdated");
                map.put("priceId", priceId);
                map.put("price", price);
                map.put("timeUpdated", timeupdated);
            }
            return map;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeConnection()
    {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
