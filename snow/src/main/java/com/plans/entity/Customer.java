package com.plans.entity;

/**
 * Created by river on 2016/3/24.
 */

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Customer {

    @PrimaryKey
    private final String name;
    @Column
    private final String date;
    @Column
    private final String type;
    @Column
    private final String credentials;
    @Column
    private final String credentials_no;
    @Column
    private final String contact;
    @Column
    private final String flight;
    @Column
    private final String depart;
    @Column
    private final String dest;
    @Column
    private final String seat;
    @Column
    private final String airport;
    @Column
    private final String carriage;
    @Column
    private final String station;

    public Customer(String name, String date, String type, String credentials, String credentials_no, String contact, String flight, String depart, String dest, String seat, String airport, String carriage, String station) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.credentials = credentials;
        this.credentials_no = credentials_no;
        this.contact = contact;
        this.flight = flight;
        this.depart = depart;
        this.dest = dest;
        this.seat = seat;
        this.airport = airport;
        this.carriage = carriage;
        this.station = station;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getCredentials() {
        return credentials;
    }

    public String getCredentials_no() {
        return credentials_no;
    }

    public String getContact() {
        return contact;
    }

    public String getFlight() {
        return flight;
    }

    public String getDepart() {
        return depart;
    }

    public String getDest() {
        return dest;
    }

    public String getSeat() {
        return seat;
    }

    public String getAirport() {
        return airport;
    }

    public String getCarriage() {
        return carriage;
    }

    public String getStation() {
        return station;
    }
}
