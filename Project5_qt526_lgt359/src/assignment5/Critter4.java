/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Lorrie Tria
 * LGT359
 * Quoc Truong
 * QT526
 *
 * Spring 2018
 */

package assignment5;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * NAME: The Drunk Rock
 * ATTRIBUTES: This critter always wants to fight!!!
 * MOVEMENT(doTimeStep): Always runs and charges randomly
 * SHAPE/COLOR: Circle, Yellow
 */
public class Critter4 extends Critter {

    @Override
    public void doTimeStep() {
        int random = Critter.getRandomInt(8);

        if(look(random, true) != null){
            super.run(random);
        }
        else {
            super.run(0);
        }

    }

    @Override
    public boolean fight(String opponent)
    {
        return true;
    }

    public String toString() {
        return "4";
    }

    @Override
    public CritterShape viewShape() { return CritterShape.CIRCLE; }

    @Override
    public javafx.scene.paint.Color viewColor() { return Color.YELLOW; }
}
