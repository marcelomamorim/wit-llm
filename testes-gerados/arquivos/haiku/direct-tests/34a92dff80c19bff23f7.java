/*
 * @test
 * @summary Tests Module.isOpen(String) on java.base for null, unknown package and exported-but-not-open package
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        Module base = String.class.getModule();

        boolean npe = false;
        try {
            base.isOpen(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("isOpen(null) should throw NullPointerException");
        }

        if (base.isOpen("no.such.pkg")) {
            throw new RuntimeException("unknown package must not be open");
        }

        if (base.isOpen("java.lang")) {
            throw new RuntimeException("java.lang is exported but not open in java.base");
        }

        System.out.println("PASS");
    }
}
