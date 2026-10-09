/* @test
   @summary Test the set method of BasicConstraintsExtension for valid and invalid inputs.
   @run main BasicConstraintsExtensionWitupTest_Pc99db3456cd3
 */
import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest_Pc99db3456cd3 {
    public static void main(String[] args) throws Exception {
        testSetWithValidBoolean();
        testSetWithInvalidBoolean();
        testSetWithValidInteger();
        testSetWithInvalidInteger();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidBoolean() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("is_ca", Boolean.FALSE);
        System.out.println("testSetWithValidBoolean passed");
    }

    private static void testSetWithInvalidBoolean() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("is_ca", "notABoolean");
            throw new AssertionError("Expected IOException for invalid Boolean");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidBoolean passed");
        }
    }

    private static void testSetWithValidInteger() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
        ext.set("path_len", Integer.valueOf(5));
        System.out.println("testSetWithValidInteger passed");
    }

    private static void testSetWithInvalidInteger() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("path_len", "notAnInteger");
            throw new AssertionError("Expected IOException for invalid Integer");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidInteger passed");
        }
    }

    private static void testSetWithUnrecognizedName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
            ext.set("unknown_attribute", Boolean.TRUE);
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            System.out.println("testSetWithUnrecognizedName passed");
        }
    }
}
