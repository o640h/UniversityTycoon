/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

public enum BuildingTypes {
    SmallAccommodation("Accommodation"),
    MediumAccommodation("Accommodation"),
    LargeAccommodation("Accommodation"),
    DiningHall("Food & Drink"),
    Cafe("Food & Drink"),
    ConvenienceStore("Food & Drink"),
    CommonRoom("Leisure"),
    StudentBar("Leisure"),
    BasketballCourt("Leisure"),
    StemBuilding("Teaching"),
    HumanitiesBuilding("Teaching"),
    ArtsBuilding("Teaching"),
    Library("Teaching");

    public final String category;

    private BuildingTypes(String category) {
        this.category = category;
    }
}

