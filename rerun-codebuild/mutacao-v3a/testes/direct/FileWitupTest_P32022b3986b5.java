/*
 * @test
 * @summary Test for File.setLastModified method handling positive, zero, and negative timestamps.
 * @run main FileWitupTest_P32022b3986b5
 */

import java.io.File;

public class FileWitupTest_P32022b3986b5 {

    public static void main(String[] args) {
        testSetLastModifiedPositive();
        testSetLastModifiedZero();
        testSetLastModifiedNegative();
    }

    private static void testSetLastModifiedPositive() {
        try {
            File file = new File("testfile_positive");
            file.createNewFile();
            long currentTime = System.currentTimeMillis();
            boolean result = file.setLastModified(currentTime);
            System.out.println("Positive timestamp test: " + result);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("Exception in positive timestamp test: " + e);
        }
    }

    private static void testSetLastModifiedZero() {
        try {
            File file = new File("testfile_zero");
            file.createNewFile();
            long zeroTime = 0L;
            boolean result = file.setLastModified(zeroTime);
            System.out.println("Zero timestamp test: " + result);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("Exception in zero timestamp test: " + e);
        }
    }

    private static void testSetLastModifiedNegative() {
        try {
            File file = new File("testfile_negative");
            file.createNewFile();
            long negativeTime = -1000L;
            boolean result = file.setLastModified(negativeTime);
            System.out.println("Negative timestamp test: " + result);
            if (file.exists()) {
                file.delete();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Expected exception for negative timestamp: " + e);
        } catch (Exception e) {
            System.out.println("Unexpected exception in negative timestamp test: " + e);
        }
    }
}
