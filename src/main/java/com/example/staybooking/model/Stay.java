package com.example.staybooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="stay")
@JsonDeserialize(builder = Stay.Builder.class)
public class Stay implements Serializable {
    private static final long serialVersionUID=2L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Type(type="text")
    private String description;
    private String address;
    @JsonProperty("guest_number")
    private int guestNumber;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User host;

    @JsonIgnore
    @OneToMany(mappedBy = "stay",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<StayAvailability> availabilities;

    @OneToMany(mappedBy = "stay",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StayImage> stayImages;

    public List<StayImage> getstayImages() {
        return stayImages;
    }

    public void setstayImages(List<StayImage> stayImages) {
        this.stayImages = stayImages;
    }

    public Stay() {}
    private Stay(Builder builder) {
        //this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.address = builder.address;
        this.guestNumber = builder.guestNumber;
        this.host = builder.host;
        this.availabilities = builder.availabilities;
        this.stayImages=builder.stayImages;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesciption() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public User getHost() {
        return host;
    }

    @JsonIgnore
    public List<StayAvailability> getStayAvailabilityList() {
        return availabilities;
    }

    public void setAvailabilities(List<StayAvailability> availabilities) {
        this.availabilities = availabilities;
    }

    public static class Builder {
        @JsonProperty("id")
        private long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("address")
        private String address;

        @JsonProperty("guest_number")
        private int guestNumber;

        @JsonProperty("host")
        private User host;

        @JsonProperty("availabilities")
        private List<StayAvailability> availabilities;

        @JsonProperty("images")
        private List<StayImage> stayImages;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setGuestNumber(int guestNumber) {
            this.guestNumber = guestNumber;
            return this;
        }

        public Builder setHost(User host) {
            this.host = host;
            return this;
        }

        public Builder setAvailabilities(List<StayAvailability> availabilities) {
            this.availabilities = availabilities;
            return this;
        }

        public Builder setstayImages(List<StayImage> stayImages) {
            this.stayImages = stayImages;
            return this;
        }

        public Stay build() {
            return new Stay(this);
        }
    }


}
