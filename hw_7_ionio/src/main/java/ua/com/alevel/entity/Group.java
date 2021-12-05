package ua.com.alevel.entity;

public class Group extends BaseEntity {

    private String name;
    private String curator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", curator='" + curator + '\'' +
                '}';
    }
}