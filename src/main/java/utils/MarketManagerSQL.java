package utils;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Random;

public class MarketManagerSQL {
    Connection connection = new MySQLUtils().getConnection();
    Statement statement = null;

    public LinkedHashMap selectManager()
    {
        LinkedHashMap map = new LinkedHashMap();
        String sql = "select managerid, validity, enthusiasm_rate, optimism_rate, extreme_rate from market_manager";
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
                    map.put("extremeRate", rs.getDouble("extreme_rate"));
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

    public int autoUpdate() throws InterruptedException {
        Random random = new Random();
        boolean increase1 = random.nextBoolean();
        double random1 = random.nextDouble() / 10;
        if(!increase1)
        {
            random1 = -random1 * 0.90;
        }
        double random2 = random.nextDouble() / 10;
        if(!increase1)
        {
            random2 = -random2 * 0.90;
        }
        double random3 = random.nextDouble() / 10;
        if(!increase1)
        {
            random3 = -random3 * 0.90;
        }
        String sql = "update market_manager set enthusiasm_rate = enthusiasm_rate + ?, optimism_rate = optimism_rate + ?, extreme_rate = extreme_rate + ? where managerid = 1";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setObject(1, random1);
            ps.setObject(2, random2);
            ps.setObject(3, random3);
            int count = ps.executeUpdate();
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int resetMarketManager()
    {
        String sql = "update market_manager set enthusiasm_rate = 1, optimism_rate = 1, extreme_rate = 1 where managerid = 1";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            int count = ps.executeUpdate();
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
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
