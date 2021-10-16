package ru.currency.service;

import lombok.Data;

import java.util.Date;

@Data

public class Model {
    private String Name;
    private Date Date;
    private Double Value;
    private int Nominal;
}
