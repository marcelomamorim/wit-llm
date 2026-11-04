/* @test    @summary Test for ModuleHashes constructor handling null map    @run main ModuleHashesWitupTest */

import java.util.Map;
import java.util.HashMap;
import jdk.internal.module.ModuleHashes;

public class ModuleHashesWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullMap();
    }

    private static void testConstructorWithNullMap() {
        String algorithm = "SHA-256";
        Map<String, byte[]> nullMap = null;
        try {
            new ModuleHashes(algorithm, nullMap);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
