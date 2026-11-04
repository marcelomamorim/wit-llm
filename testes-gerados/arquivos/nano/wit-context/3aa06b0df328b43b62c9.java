/* @test    @summary Test for sun.security.x509.X500Name.X500Name(String dname, String format)    @run main X500NameWitupTest */

import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testNullDname();
        testUnsupportedFormat();
        testDefaultFormat();
    }

    private static void testNullDname() {
        try {
            new X500Name(null, "RFC2253");
            System.err.println("Failed: Expected NullPointerException for null dname");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null dname");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null dname: " + e);
        }
    }

    private static void testUnsupportedFormat() {
        String dname = "CN=Test";
        String unsupportedFormat = "UNKNOWN_FORMAT";
        try {
            new X500Name(dname, unsupportedFormat);
            System.err.println("Failed: Expected IOException for unsupported format");
        } catch (IOException e) {
            System.out.println("Passed: IOException thrown as expected for unsupported format");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for unsupported format: " + e);
        }
    }

    private static void testDefaultFormat() {
        String dname = "CN=Test";
        try {
            X500Name name = new X500Name(dname, "DEFAULT");
            if (name != null) {
                System.out.println("Passed: X500Name created with DEFAULT format");
            } else {
                System.err.println("Failed: X500Name is null for DEFAULT format");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception thrown for DEFAULT format: " + e);
        }
    }
}
