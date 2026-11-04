/* @test    @summary BasicPermission constructor with Class parameter behavior test    @run main BasicPermissionWitupTest */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    public static void main(String[] args) {
        testConstructorWithClass();
    }

    public static void testConstructorWithClass() {
        try {
            Class<?> clazz = String.class;
            BasicPermission bp = new BasicPermission("test", clazz);
            if (bp == null) {
                throw new AssertionError("BasicPermission instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
