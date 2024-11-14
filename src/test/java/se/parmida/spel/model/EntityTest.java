package se.parmida.spel.model;

import org.junit.jupiter.api.Assertions;

class EntityTest {

   @org.junit.jupiter.api.Test
    void takeHit() {
       Resident resident = new Resident("resident", 9, 10);
       Burglar burglar = new Burglar("burglar", 11, 8);
       burglar.takeHit(5);
       Assertions.assertEquals(6, burglar.getHealth());
   }

    @org.junit.jupiter.api.Test
    void punch() {
        Resident resident= new Resident("resident", 9, 10);
        Burglar burglar = new Burglar("burglar", 11, 8);
        resident.punch(burglar);
        Assertions.assertEquals(1, burglar.getHealth());
    }

    @org.junit.jupiter.api.Test
    void isConsciousTrue() {
        Resident resident = new Resident("resident", 9, 10);
        Burglar burglar = new Burglar("burglar", 11, 8);
        resident.punch(burglar);
        Assertions.assertTrue(burglar.isConscious());
    }

    @org.junit.jupiter.api.Test
    void isConsciousFalse() {
        Resident resident = new Resident("resident", 9, 20);
        Burglar burglar = new Burglar("burglar", 11, 8);
        resident.punch(burglar);
        Assertions.assertFalse(burglar.isConscious());
    }
}