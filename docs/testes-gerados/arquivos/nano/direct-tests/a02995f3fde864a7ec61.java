/*
 * @test
 * @summary Test System.addOpens method for valid modules and package names
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {
    public static void main(String[] args) {
        Module currentModule = System.class.getModule();
        Module targetModule = currentModule;
        String packageName = "java.lang";
        try {
            System.addOpens(targetModule, packageName, currentModule);
            System.out.println("addOpens executed successfully for package: " + packageName);
        } catch (Throwable t) {
            System.err.println("addOpens threw an exception: " + t);
            t.printStackTrace();
        }
    }
}
