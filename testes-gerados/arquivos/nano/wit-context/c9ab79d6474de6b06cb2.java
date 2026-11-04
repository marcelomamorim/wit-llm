/*
 * @test
 * @summary Tests for ContentInfo constructors to verify exception paths.
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;
import java.io.IOException;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullContentType();
        testConstructorWithNullContent();
    }

    public static void testConstructorWithNullContentType() {
        try {
            new ContentInfo(null, new DerValue(new byte[0]));
            System.out.println("Failed: Expected NullPointerException for null contentType");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException caught as expected for null contentType");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for null contentType: " + e);
        }
    }

    public static void testConstructorWithNullContent() {
        try {
            ObjectIdentifier oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 7, 1});
            new ContentInfo(oid, null);
            System.out.println("Passed: Constructor accepted null content");
        } catch (Exception e) {
            System.out.println("Failed: Exception thrown for null content: " + e);
        }
    }
}
