package com.example.AttendanceApp.models;

public enum Separate {
    PRODUCE("Produce"),
    DAIRY("Dairy"),
    BAKERY("Bakery "),
    MEAT_AND_SEAFOOD("Meat & Seafood"),
    DRY_GOODS("Dry Goods"),
    FROZEN_FOODS("Frozen Foods"),
    HOUSEHOLD_ITEMS("Household Items");

    private final String title;

    Separate(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
