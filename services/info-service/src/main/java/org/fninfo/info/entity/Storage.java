package org.fninfo.info.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="event_name")
    private String name;

    @Column(name = "status", nullable = true)
    private boolean isPositive;

    @Column
    private String text;

    @Column
    private String image;

    public Storage() {
    }

    public Storage(Integer id, String name, boolean isPositive, String text, String image) {
        this.id = id;
        this.name = name;
        this.isPositive = isPositive;
        this.text = text;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
