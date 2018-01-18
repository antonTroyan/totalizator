package by.troyan.web.entity;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

/**
 * Describe sport Event. Each event must have league, members and so on.
 * Event date must be greater than current date.
 */

public class Event {
    private int leagueId;
    private String eventName;
    private Time eventTime;
    private Date eventDate;
    private String eventLeague;
    private int rateCount;
    private String rateTypes;
    private int eventId;
    private String liveTranslationLink;
    private List<Member> members;
    private List<Integer> memberIds;
    private String status;
    private EventResult result;
    private boolean canMakeRate;
    private boolean canAddResult;
    private double coefficient;

    public Event() {}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLeague() {
        return eventLeague;
    }

    public void setEventLeague(String eventLeague) {
        this.eventLeague = eventLeague;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getLiveTranslationLink() {
        return liveTranslationLink;
    }

    public void setLiveTranslationLink(String liveTranslationLink) {
        this.liveTranslationLink = liveTranslationLink;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getRateTypes() {
        return rateTypes;
    }

    public void setRateTypes(String rateTypes) {
        this.rateTypes = rateTypes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public EventResult getResult() {
        return result;
    }

    public void setResult(EventResult result) {
        this.result = result;
    }

    public void setCanMakeRate(boolean canMakeRate) {
        this.canMakeRate = canMakeRate;
    }

    public void setCanAddResult(boolean canAddResult) {
        this.canAddResult = canAddResult;
    }

    public boolean isCanMakeRate() {
        return canMakeRate;
    }

    public boolean isCanAddResult() {
        return canAddResult;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return leagueId == event.leagueId &&
                rateCount == event.rateCount &&
                eventId == event.eventId &&
                canMakeRate == event.canMakeRate &&
                canAddResult == event.canAddResult &&
                Double.compare(event.coefficient, coefficient) == 0 &&
                Objects.equals(eventName, event.eventName) &&
                Objects.equals(eventTime, event.eventTime) &&
                Objects.equals(eventDate, event.eventDate) &&
                Objects.equals(eventLeague, event.eventLeague) &&
                Objects.equals(rateTypes, event.rateTypes) &&
                Objects.equals(liveTranslationLink, event.liveTranslationLink) &&
                Objects.equals(members, event.members) &&
                Objects.equals(memberIds, event.memberIds) &&
                Objects.equals(status, event.status) &&
                Objects.equals(result, event.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leagueId, eventName, eventTime, eventDate, eventLeague, rateCount, rateTypes, eventId, liveTranslationLink, members, memberIds, status, result, canMakeRate, canAddResult, coefficient);
    }

    @Override
    public String toString() {
        return "Event{" +
                "leagueId=" + leagueId +
                ", eventName='" + eventName + '\'' +
                ", eventTime=" + eventTime +
                ", eventDate=" + eventDate +
                ", eventLeague='" + eventLeague + '\'' +
                ", rateCount=" + rateCount +
                ", rateTypes='" + rateTypes + '\'' +
                ", eventId=" + eventId +
                ", liveTranslationLink='" + liveTranslationLink + '\'' +
                ", members=" + members +
                ", memberIds=" + memberIds +
                ", status='" + status + '\'' +
                ", result=" + result +
                ", canMakeRate=" + canMakeRate +
                ", canAddResult=" + canAddResult +
                ", coefficient=" + coefficient +
                '}';
    }
}
