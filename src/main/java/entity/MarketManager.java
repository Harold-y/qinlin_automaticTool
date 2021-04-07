package entity;

import java.util.Date;

public class MarketManager {
    private int managerId;
    private int validity;
    private double optimismRate;
    private double enthusiasmRate;
    private Date timeRange;
    private int userId;
    private Date timeUpdated;

    public MarketManager() {
    }

    public MarketManager(int managerId, int validity, double optimismRate, double enthusiasmRate, Date timeRange, int userId, Date timeUpdated) {
        this.managerId = managerId;
        this.validity = validity;
        this.optimismRate = optimismRate;
        this.enthusiasmRate = enthusiasmRate;
        this.timeRange = timeRange;
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

    public Date getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Date timeRange) {
        this.timeRange = timeRange;
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
