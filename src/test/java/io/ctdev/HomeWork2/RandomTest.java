//Using Random java class Random rand = new Random();
// Validate that random returns values in the correct range ( for example use range 10)
package io.ctdev.HomeWork2;


import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertTrue;


public class RandomTest {
    int range = 11;

    @Test
    public void randomTest() {

        Random rand = new Random();
        int intRandom = rand.nextInt(range);
        assertTrue( intRandom >=1 && intRandom <=10, "Values are not in the correct range");
        System.out.println("Random integer value from 0 to " + (range-1) + " : "+ intRandom);
    }
}
