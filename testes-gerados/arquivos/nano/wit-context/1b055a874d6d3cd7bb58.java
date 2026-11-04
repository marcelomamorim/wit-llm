/* @test    @summary Test for GeneralNames.add() method handling null input    @run main GeneralNamesWitupTest */

import sun.security.x509.GeneralNames;
import sun.security.x509.GeneralName;

public class GeneralNamesWitupTest {

    public static void main(String[] args) {
        testAddNullThrowsNullPointerException();
        testAddValidName();
    }

    private static void testAddNullThrowsNullPointerException() {
        GeneralNames gn = new GeneralNames();
        try {
            gn.add(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testAddValidName() {
        GeneralNames gn = new GeneralNames();
        GeneralName name = new GeneralName(); // Assuming default constructor exists
        try {
            gn.add(name);
            if (gn.size() != 1 || gn.get(0) != name) {
                System.err.println("Failed: Name not added correctly");
                System.exit(1);
            } else {
                System.out.println("Passed: Valid name added successfully");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
