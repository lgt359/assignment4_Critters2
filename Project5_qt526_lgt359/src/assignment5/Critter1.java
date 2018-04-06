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

import java.util.*;

/**
 * NAME: The Straight-Edge
 * ATTRIBUTES: This critter only fights when it has enough energy (>50)
 * MOVEMENT(doTimeStep): Always moves horizontally or vertically (randomly)
 * SHAPE/COLOR: Star, Red
 */
public class Critter1 extends Critter {

    @Override
    public void doTimeStep() {
        int random = Critter.getRandomInt(8);

        // up, down, left, right
        if(random%2 != 0){
            random--;
        }
        super.walk(random);
    }

    @Override
    public boolean fight(String opponent) {
        // always fights algae for energy
        if (opponent.equals("@")){
            return true;
        }

        // fights if has enough energy
        if (this.getEnergy() > 50) return true;
            // runs away
        else {
            this.run(getRandomInt(8));
            return false;
        }
    }

    public String toString() {
        return "1";
    }

    @Override
    public CritterShape viewShape() { return CritterShape.STAR; }

    @Override
    public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.RED; }
}
