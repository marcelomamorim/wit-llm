/*
 * @test
 * @summary Test for ProxyGenerator.getClass(String) method, focusing on null input.
 * @run main ProxyGeneratorWitupTest
 */

import java.lang.reflect.ProxyGenerator;

public class ProxyGeneratorWitupTest {

    public static void main(String[] args) {
        testGetClassWithNullName();
        testGetClassWithValidName();
    }

    private static void testGetClassWithNullName() {
        try {
            ProxyGenerator pg = new ProxyGenerator();
            short result = pg.getClass(null);
            System.out.println("testGetClassWithNullName: Unexpected success, result=" + result);
        } catch (NullPointerException npe) {
            System.out.println("testGetClassWithNullName: Caught expected NullPointerException");
        } catch (Exception e) {
            System.out.println("testGetClassWithNullName: Caught unexpected exception: " + e);
        }
    }

    private static void testGetClassWithValidName() {
        try {
            String validName = "TestName";
            ProxyGenerator pg = new ProxyGenerator();
            short result = pg.getClass(validName);
            System.out.println("testGetClassWithValidName: Result=" + result);
        } catch (Exception e) {
            System.out.println("testGetClassWithValidName: Unexpected exception: " + e);
        }
    }
}
