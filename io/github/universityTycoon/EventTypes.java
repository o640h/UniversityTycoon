/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

public enum EventTypes {
    FLOODING(1, "Flooding has occured in the campus.", "images/tree.png", false),
    HURRICANE(3, "Bad weather has caused damage to the buildings.", "images/tree.png", false),
    COFFEE_MACHINE_BREAKDOWN(4, "The coffee machine has broken.", "images/tree.png", false),
    STUDENT_PROTEST(2, "Students protest about poor conditions on campus.", "images/tree.png", true),
    CELEBRITY_GUEST(2, "A celebrity guest will be giving a lecture in campus.", "images/tree.png", true),
    FOOTBALL_VICTORY(3, "The university football team beat their local rivals.", "images/tree.png", false),
    ANONYMOUS_GRANT(1, "A large anonymous donation has been made to the university.", "images/tree.png", false),
    CULTURAL_FAIR(4, "A cultural fair has been organised by the university for the weekend.", "images/tree.png", true),
    GOOD_WEATHER(4, "A few days of good weather have improved everyone's mood.", "images/tree.png", false),
    GEESE_INVASION(2, "A flock of geese have invaded the campus.", "images/tree.png", false);

    private final String description;
    private final int rarity;
    private final String iconPath;
    private boolean planned;

    private EventTypes(int rarity, String description, String iconPath, boolean planned) {
        if (rarity < 1 || rarity > 4) {
            throw new IllegalArgumentException("Invalid rarity");
        }
        if (iconPath == null) {
            throw new IllegalArgumentException("Invalid icon file path");
        }
        this.rarity = rarity;
        this.description = description;
        this.iconPath = iconPath;
        this.planned = planned;
    }

    public int getRarity() {
        return this.rarity;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public boolean getPlanned() {
        return this.planned;
    }
}

