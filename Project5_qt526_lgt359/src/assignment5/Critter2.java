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
 * NAME: The Diagonal
 * ATTRIBUTES: This critter only fights when it has enough energy (>50)
 * MOVEMENT(doTimeStep): Always moves diagonally (randomly)
 * SHAPE/COLOR: Diamond, Purple
 */
public class Critter2 extends Critter.TestCritter {

    @Override
    public void doTimeStep() {
        int random = Critter.getRandomInt(8);

        // mods to be a number corresponding to diagonals
        if(random%2 == 0){
            random++;
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
            // run away
        else {
            this.run(getRandomInt(8));
            return false;
        }
    }

    public String toString() {
        return "2";
    }

    @Override
    public CritterShape viewShape() { return CritterShape.DIAMOND; }

    @Override
    public javafx.scene.paint.Color viewColor() { return Color.PURPLE; }
}
