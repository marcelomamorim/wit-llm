/*
 * @test
 * @summary Testa o método System.log para comportamento padrão.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;

public class SystemWitupTest {
    public static void main(String[] args) {
        testLogWithNullMsgSupplier();
    }

    private static void testLogWithNullMsgSupplier() {
        Logger logger = System.getLogger("TestLogger");
        Level level = Level.INFO;
        Throwable thrown = new NullPointerException("Test exception");
        try {
            logger.log(level, null, thrown);
            System.out.println("Test passed: log with null msg supplier did not throw.");
        } catch (NullPointerException e) {
            System.out.println("Test failed: log threw NullPointerException.");
        }
    }
}
