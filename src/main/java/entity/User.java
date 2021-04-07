package entity;

import java.util.Date;

public class User {
    private int userid;
    private String username;
    private String phone;
    private String emailAddr;
    private String password;
    private String realName;
    private String uuid;
    private Date timeCreated;

    public User() {
    }

    public User(int userid, String username, String phone, String emailAddr, String password, String realName, String uuid, Date timeCreated) {
        this.userid = userid;
        this.username = username;
        this.phone = phone;
        this.emailAddr = emailAddr;
        this.password = password;
        this.realName = realName;
        this.uuid = uuid;
        this.timeCreated = timeCreated;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
