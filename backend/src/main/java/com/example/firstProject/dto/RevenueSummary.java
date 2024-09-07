package com.example.firstProject.dto;

public class RevenueSummary {

    private double revenueForCurrentDay;

    private double revenueForCurrentMonth;

    private double revenueForCurrentYear;

    public double getRevenueForCurrentDay() {
        return revenueForCurrentDay;
    }

    public void setRevenueForCurrentDay(double revenueForCurrentDay) {
        this.revenueForCurrentDay = revenueForCurrentDay;
    }

    public double getRevenueForCurrentMonth() {
        return revenueForCurrentMonth;
    }

    public void setRevenueForCurrentMonth(double revenueForCurrentMonth) {
        this.revenueForCurrentMonth = revenueForCurrentMonth;
    }

    public double getRevenueForCurrentYear() {
        return revenueForCurrentYear;
    }

    public void setRevenueForCurrentYear(double revenueForCurrentYear) {
        this.revenueForCurrentYear = revenueForCurrentYear;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    private String Currency="INR";
}

