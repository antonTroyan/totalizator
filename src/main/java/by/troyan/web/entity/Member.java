package by.troyan.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describe Member. Each event must have at least 2 members.
 */

public class Member {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    private int categoryId;
    private int leagueId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != member.id) return false;
        if (categoryId != member.categoryId) return false;
        if (leagueId != member.leagueId) return false;
        return name != null ? name.equals(member.name) : member.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + leagueId;
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", leagueId=" + leagueId +
                '}';
    }
}
