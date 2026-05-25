package com.model;


import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private int mob_no;

    @Column(unique=true)
    private String email;

    @Column(nullable = false)
    private int dl_no;

    @OneToOne
    public User user;

    public Customer(User user) {
        this.user = user;
    }

    public Customer() {

    }

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

    public int getMob_no() {
        return mob_no;
    }

    public void setMob_no(int mob_no) {
        this.mob_no = mob_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDl_no() {
        return dl_no;
    }

    public void setDl_no(int dl_no) {
        this.dl_no = dl_no;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mob_no=" + mob_no +
                ", email='" + email + '\'' +
                ", dl_no=" + dl_no +
                '}';
    }
}
