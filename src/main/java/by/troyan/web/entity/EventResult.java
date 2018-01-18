package by.troyan.web.entity;

/**
 * Describe EventResult. Should be filled by moderator after event ended.
 */

public class EventResult {
    private int eventId;
    private int winnerId;
    private int loserId;
    private int winnerScore;
    private int loserScore;
    private String winnerName;
    private String loserName;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public int getLoserId() {
        return loserId;
    }

    public void setLoserId(int loserId) {
        this.loserId = loserId;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventResult that = (EventResult) o;

        if (eventId != that.eventId) return false;
        if (winnerId != that.winnerId) return false;
        if (loserId != that.loserId) return false;
        if (winnerScore != that.winnerScore) return false;
        if (loserScore != that.loserScore) return false;
        if (winnerName != null ? !winnerName.equals(that.winnerName) : that.winnerName != null) return false;
        return loserName != null ? loserName.equals(that.loserName) : that.loserName == null;
    }

    @Override
    public int hashCode() {
        int result = eventId;
        result = 31 * result + winnerId;
        result = 31 * result + loserId;
        result = 31 * result + winnerScore;
        result = 31 * result + loserScore;
        result = 31 * result + (winnerName != null ? winnerName.hashCode() : 0);
        result = 31 * result + (loserName != null ? loserName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventResult{" +
                "eventId=" + eventId +
                ", winnerId=" + winnerId +
                ", loserId=" + loserId +
                ", winnerScore=" + winnerScore +
                ", loserScore=" + loserScore +
                ", winnerName='" + winnerName + '\'' +
                ", loserName='" + loserName + '\'' +
                '}';
    }
}
