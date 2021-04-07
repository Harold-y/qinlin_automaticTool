package utils;

import java.sql.*;
import java.util.LinkedHashMap;

public class PriceManagerSQL {
    Connection connection = new MySQLUtils().getConnection();
    Statement statement = null;

    public LinkedHashMap selectManager(int coinId)
    {
        LinkedHashMap map = new LinkedHashMap();
        String sql = "select managerid, validity, enthusiasm_rate, optimism_rate, assume_level, timerange, pricefloor, priceceiling from price_manager where coinid = "+coinId;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int validity = rs.getInt("validity");
                if(validity == 1)
                {
                    map.put("managerId", rs.getInt("managerid"));
                    map.put("validity", validity);
                    map.put("enthusiasmRate", rs.getDouble("enthusiasm_rate"));
                    map.put("optimismRate", rs.getDouble("optimism_rate"));
                    map.put("assumeLevel", rs.getDouble("assume_level"));
                    map.put("timeRange", rs.getDate("timerange"));
                    map.put("pricefloor", rs.getDouble("pricefloor"));
                    map.put("priceceiling", rs.getDouble("priceceiling"));
                    return map;
                }
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            return map;
        }
        return map;
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
