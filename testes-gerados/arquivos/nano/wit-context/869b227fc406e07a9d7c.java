/*
 * @test
 * @summary Test for java.io.FileOutputStream constructors and exception paths
 * @run main FileOutputStreamWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullFile();
        testConstructorWithNonExistentFile();
    }

    public static void testConstructorWithNullFile() {
        try {
            new FileOutputStream((File) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    public static void testConstructorWithNonExistentFile() {
        File nonExistentFile = new File("non_existent_file.txt");
        try {
            FileOutputStream fos = new FileOutputStream(nonExistentFile);
            fos.close();
        } catch (FileNotFoundException e) {
            // Expected if file cannot be created or opened
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
