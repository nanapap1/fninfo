package org.fninfo.info.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "storage")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nameEvent")
    private String nameEvent;

    @Column(name = "statusOf", nullable = true)
    private boolean statusOf;

    @Column
    private String text;

    @Column
    private String image;

    public Info() {
    }

    public Info(Integer id, String name, boolean isPositive, String text, String image) {
        this.id = id;
        this.nameEvent = name;
        this.statusOf = isPositive;
        this.text = text;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public boolean isStatusOf() {
        return statusOf;
    }

    public void setStatusOf(boolean statusOf) {
        this.statusOf = statusOf;
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
