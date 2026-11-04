/* @test    @summary Test for Attributes.Name constructor handling null and invalid input    @run main AttributesWitupTest */

import java.util.jar.Attributes;

public class AttributesWitupTest {

    public static void main(String[] args) {
        testNullName();
        testEmptyName();
        testLongName();
    }

    private static void testNullName() {
        try {
            new Attributes.Name(null);
            System.out.println("FAIL: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null name");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null name: " + e);
        }
    }

    private static void testEmptyName() {
        try {
            Attributes.Name name = new Attributes.Name("");
            if (name != null && name.name.equals("") && name.hashCode() == name.hash("") && name.name.intern().equals("") ) {
                System.out.println("PASS: Empty name handled correctly");
            } else {
                System.out.println("FAIL: Empty name not handled as expected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown for empty name: " + e);
        }
    }

    private static void testLongName() {
        String longName = new String(new char[71]).replace("\0", "a");
        try {
            Attributes.Name name = new Attributes.Name(longName);
            System.out.println("FAIL: Expected IllegalArgumentException for long name, but got: " + name);
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for long name");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for long name: " + e);
        }
    }
}
