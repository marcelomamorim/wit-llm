/*
 * @test
 * @summary Test java.io.File.canExecute() method for various file states.
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testCanExecuteWithExistingFile();
        testCanExecuteWithNonExistingFile();
        testCanExecuteWithDirectory();
    }

    private static void testCanExecuteWithExistingFile() {
        try {
            File file = new File(".");
            boolean result = file.canExecute();
            System.out.println("Existing directory canExecute: " + result);
        } catch (SecurityException se) {
            System.out.println("SecurityException caught in testCanExecuteWithExistingFile");
        }
    }

    private static void testCanExecuteWithNonExistingFile() {
        try {
            File file = new File("non_existing_file_" + System.nanoTime());
            boolean result = file.canExecute();
            System.out.println("Non-existing file canExecute: " + result);
        } catch (SecurityException se) {
            System.out.println("SecurityException caught in testCanExecuteWithNonExistingFile");
        }
    }

    private static void testCanExecuteWithDirectory() {
        try {
            File dir = new File(System.getProperty("user.dir"));
            boolean result = dir.canExecute();
            System.out.println("User directory canExecute: " + result);
        } catch (SecurityException se) {
            System.out.println("SecurityException caught in testCanExecuteWithDirectory");
        }
    }
}
