package entity;

import java.util.Date;

public class PriceManager {
    private int managerId;
    private int validity;
    private int coinId;
    private double optimismRate;
    private double enthusiasmRate;
    private double assumeLevel;
    private Date timeRange;
    private double priceFloor;
    private double priceCeiling;
    private int userId;
    private Date timeUpdated;

    public PriceManager() {
    }

    public PriceManager(int managerId, int validity, int coinId, double optimismRate, double enthusiasmRate, double assumeLevel, Date timeRange, double priceFloor, double priceCeiling, int userId, Date timeUpdated) {
        this.managerId = managerId;
        this.validity = validity;
        this.coinId = coinId;
        this.optimismRate = optimismRate;
        this.enthusiasmRate = enthusiasmRate;
        this.assumeLevel = assumeLevel;
        this.timeRange = timeRange;
        this.priceFloor = priceFloor;
        this.priceCeiling = priceCeiling;
        this.userId = userId;
        this.timeUpdated = timeUpdated;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public double getOptimismRate() {
        return optimismRate;
    }

    public void setOptimismRate(double optimismRate) {
        this.optimismRate = optimismRate;
    }

    public double getEnthusiasmRate() {
        return enthusiasmRate;
    }

    public void setEnthusiasmRate(double enthusiasmRate) {
        this.enthusiasmRate = enthusiasmRate;
    }

    public double getAssumeLevel() {
        return assumeLevel;
    }

    public void setAssumeLevel(double assumeLevel) {
        this.assumeLevel = assumeLevel;
    }

    public Date getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Date timeRange) {
        this.timeRange = timeRange;
    }

    public double getPriceFloor() {
        return priceFloor;
    }

    public void setPriceFloor(double priceFloor) {
        this.priceFloor = priceFloor;
    }

    public double getPriceCeiling() {
        return priceCeiling;
    }

    public void setPriceCeiling(double priceCeiling) {
        this.priceCeiling = priceCeiling;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}
