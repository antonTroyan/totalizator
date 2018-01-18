package by.troyan.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describe League. Additional category to divide events.
 */

public class League {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    private int categoryId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        League league = (League) o;

        if (id != league.id) return false;
        if (categoryId != league.categoryId) return false;
        return name != null ? name.equals(league.name) : league.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        return result;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
