/*
 * @test
 * @summary Tests for java.lang.Class methods based on WIT analysis
 * @run main ClassWitupTest
 */

import java.lang.Class;
import java.lang.System;
import java.lang.SecurityManager;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.security.AccessController;

public class ClassWitupTest {

    public static void main(String[] args) {
        testForNameNullModule();
        testForNameNullName();
    }

    public static void testForNameNullModule() {
        try {
            Class.forName(null, "java.lang.String");
            System.err.println("Expected NullPointerException for null module");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }

    public static void testForNameNullName() {
        try {
            Module module = Object.class.getModule();
            Class.forName(module, null);
            System.err.println("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
