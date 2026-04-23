/*
 * Decompiled with CFR 0.152.
 */
package io.github.universityTycoon;

public enum AchievementTypes {
    perfectScore("Perfect Score", "End the game with 100% student satisfaction.", 1.0f),
    zeroScore("Opposite Day", "End the game with 0% student satisfaction.", -1.0f),
    consistent("I Heart Uni", "Maintain over 80% student satisfaction for 3 straight minutes.", 5.0f),
    minimalist("Minimalist", "Place only 5 buildings during a game.", 0.0f),
    maximalist("Maximalist", "Place over 30 buildings during a game.", 0.0f),
    variation("Jack of All Trades", "Place exactly one of every building during a game.", 10.0f),
    crisisManagement("Crisis Management", "Successfully avert 3 negative events during a game.", 5.0f),
    foolMeTwice("Fool Me Twice", "Cause negative consequences when dealing with 2 neutral events during a game.", -5.0f);

    public final String name;
    public final String description;
    public final float scoreBonus;

    private AchievementTypes(String name, String description, float scoreBonus) {
        this.name = name;
        this.description = description;
        this.scoreBonus = scoreBonus;
    }
}

