/*
 * @test
 * @summary Test getClass method of ProxyGenerator for valid class name
 * @run main ProxyGeneratorWitupTest
 */

import java.lang.reflect.ProxyGenerator;

public class ProxyGeneratorWitupTest {
    public static void main(String[] args) {
        String className = "TestClass";
        try {
            short result = new ProxyGenerator().getClass(className);
            System.out.println("Result for class name '" + className + '": ' + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception occurred during getClass call.");
        }
    }
}
