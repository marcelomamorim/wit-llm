/*
 * @test
 * @summary Testa o método replace de java.util.Collections
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsException();
    }

    public static void testReplaceThrowsException() {
        try {
            Collections.replace(null, null);
            System.out.println("FAIL: Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null arguments");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
