package entity;

import java.util.Date;

public class Price {
    private int priceId;
    private int coinId;
    private double priceUSD;
    private Date timeUpdated;

    public Price() {
    }

    public Price(int priceId, int coinId, double priceUSD, Date timeUpdated) {
        this.priceId = priceId;
        this.coinId = coinId;
        this.priceUSD = priceUSD;
        this.timeUpdated = timeUpdated;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getCoinId() {
        return coinId;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public Date getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Date timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}
