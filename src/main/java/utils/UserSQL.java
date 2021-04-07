package utils;

import java.sql.*;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UserSQL {
    Connection connection = new MySQLUtils().getConnection();
    Statement statement = null;
    String[] names = {"Robot", "Wren", "Auto", "Reese", "Lionel","Lisa", "Root"};
    public int selectUserAmount()
    {
        String sql = "select count(*) from user";
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

        }
        return -1;
    }

    public boolean isAutoUser(int userid)
    {
        String sql = "select username from user where userid = " + userid;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ResultSet set = ps.executeQuery(sql);
            String username = null;
            while (set.next())
            {
                username = set.getString("username");
            }
            ps.close();
            boolean isRobot = false;
            String preUsername = username.split("_")[0];
            for(String name : names)
            {
                if(preUsername.equals(name))
                {
                    isRobot = true;
                    break;
                }
            }
            return isRobot;
        } catch (Exception e)
        {
            return false;
        }
    }

    public int insertIntoUser()
    {
        String sql = "insert into user (username, phone, email, password, realname, uuid, timecreated) values  (?, ?, ?, ?, ?, ?, ?)";
        Random random = new Random();
        int randomNameIndex = random.nextInt(names.length-1);
        int appendixNameIndex = random.nextInt();
        String username = names[randomNameIndex] + "_" + appendixNameIndex;
        String phone = "+861";
        for(int i = 0; i < 10; i ++)
        {
            phone += random.nextInt(10);
        }
        String email = "";
        email+= "cldCBR";
        for(int i = 0;i<7;i++)
        {
            email += i;
            email+=random.nextInt(10);
        }
        String[] emailAppendixList = {"@gmail.com", "@outlook.com", "@163.com", "@qq.com", "@yahoo.com", "@wisc.edu", "@cbr.gov"};
        int emailAppendixIndex = random.nextInt(emailAppendixList.length-1);
        String emailAppendix = emailAppendixList[emailAppendixIndex];
        email += emailAppendix;

        String password = "";
        for(int i = 0;i<7;i++)
        {
            password += random.nextInt(10);
        }
        password = AESUtils.aesEncryptStr(password, AESUtils.pkey2);
        String uuid = UUID.randomUUID().toString();
        Date timeCreated = new Date();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setObject(1, username);
            ps.setObject(2, phone);
            ps.setObject(3, email);
            ps.setObject(4, password);
            ps.setObject(5, "");
            ps.setObject(6, uuid);
            ps.setObject(7, timeCreated);
            int result = ps.executeUpdate();
            ps.close();
            return result;
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
