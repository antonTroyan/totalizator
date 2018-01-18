package by.troyan.web.entity;

import java.math.BigDecimal;

/**
 * Describe Rate. It divided into 3 parts - Win, Draw or Exact score.
 */

public class Rate {
    public static final String WIN = "WIN";
    public static final String DRAW = "DRAW";
    public static final String EXACT_SCORE = "EXACT_SCORE";

    private String eventName;
    private int eventId;
    private BigDecimal sum;
    private BigDecimal win;
    private String username;
    private int userId;
    private String type;
    private int member1Id;
    private int member2Id;
    private int member1Score;
    private int member2Score;
    private String member1Name;
    private String member2Name;
    private int rateId;


    public Rate(){

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getWin() {
        return win;
    }

    public void setWin(BigDecimal win) {
        this.win = win;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMember1Id() {
        return member1Id;
    }

    public void setMember1Id(int member1Id) {
        this.member1Id = member1Id;
    }

    public int getMember2Id() {
        return member2Id;
    }

    public void setMember2Id(int member2Id) {
        this.member2Id = member2Id;
    }

    public int getMember1Score() {
        return member1Score;
    }

    public void setMember1Score(int member1Score) {
        this.member1Score = member1Score;
    }

    public int getMember2Score() {
        return member2Score;
    }

    public void setMember2Score(int member2Score) {
        this.member2Score = member2Score;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public String getMember1Name() {
        return member1Name;
    }

    public void setMember1Name(String member1Name) {
        this.member1Name = member1Name;
    }

    public String getMember2Name() {
        return member2Name;
    }

    public void setMember2Name(String member2Name) {
        this.member2Name = member2Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (eventId != rate.eventId) return false;
        if (userId != rate.userId) return false;
        if (member1Id != rate.member1Id) return false;
        if (member2Id != rate.member2Id) return false;
        if (member1Score != rate.member1Score) return false;
        if (member2Score != rate.member2Score) return false;
        if (rateId != rate.rateId) return false;
        if (eventName != null ? !eventName.equals(rate.eventName) : rate.eventName != null) return false;
        if (sum != null ? !sum.equals(rate.sum) : rate.sum != null) return false;
        if (win != null ? !win.equals(rate.win) : rate.win != null) return false;
        if (username != null ? !username.equals(rate.username) : rate.username != null) return false;
        if (type != null ? !type.equals(rate.type) : rate.type != null) return false;
        if (member1Name != null ? !member1Name.equals(rate.member1Name) : rate.member1Name != null) return false;
        return member2Name != null ? member2Name.equals(rate.member2Name) : rate.member2Name == null;
    }

    @Override
    public int hashCode() {
        int result = eventName != null ? eventName.hashCode() : 0;
        result = 31 * result + eventId;
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (win != null ? win.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + member1Id;
        result = 31 * result + member2Id;
        result = 31 * result + member1Score;
        result = 31 * result + member2Score;
        result = 31 * result + (member1Name != null ? member1Name.hashCode() : 0);
        result = 31 * result + (member2Name != null ? member2Name.hashCode() : 0);
        result = 31 * result + rateId;
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "eventName='" + eventName + '\'' +
                ", eventId=" + eventId +
                ", sum=" + sum +
                ", win=" + win +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", member1Id=" + member1Id +
                ", member2Id=" + member2Id +
                ", member1Score=" + member1Score +
                ", member2Score=" + member2Score +
                ", member1Name='" + member1Name + '\'' +
                ", member2Name='" + member2Name + '\'' +
                ", rateId=" + rateId +
                '}';
    }
}
