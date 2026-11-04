/* @test    @summary Test ChunkedOutputStream.write(int) for basic functionality    @run main ChunkedOutputStreamWitupTest */

import java.io.*;

public class ChunkedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteSingleByte();
        testWriteMultipleBytes();
        testWriteWithNegativeByte();
        testWriteWithByteArray();
        System.out.println("All tests passed.");
    }

    static void testWriteSingleByte() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        sun.net.www.http.ChunkedOutputStream cos = new sun.net.www.http.ChunkedOutputStream(ps, 10);
        cos.write(65); // ASCII 'A'
        cos.flush();
        String output = baos.toString("US-ASCII");
        if (!output.contains("A")) {
            throw new AssertionError("Single byte write failed: " + output);
        }
        cos.close();
    }

    static void testWriteMultipleBytes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        sun.net.www.http.ChunkedOutputStream cos = new sun.net.www.http.ChunkedOutputStream(ps, 10);
        byte[] data = {65, 66, 67}; // 'A', 'B', 'C'
        cos.write(data, 0, data.length);
        cos.flush();
        String output = baos.toString("US-ASCII");
        if (!(output.contains("A") && output.contains("B") && output.contains("C"))) {
            throw new AssertionError("Multiple bytes write failed: " + output);
        }
        cos.close();
    }

    static void testWriteWithNegativeByte() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        sun.net.www.http.ChunkedOutputStream cos = new sun.net.www.http.ChunkedOutputStream(ps, 10);
        int negativeByte = -1; // 0xFF
        cos.write(negativeByte);
        cos.flush();
        String output = baos.toString("US-ASCII");
        if (!output.contains("\xff")) {
            throw new AssertionError("Negative byte write failed: " + output);
        }
        cos.close();
    }

    static void testWriteWithByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        sun.net.www.http.ChunkedOutputStream cos = new sun.net.www.http.ChunkedOutputStream(ps, 10);
        byte[] data = {0, 1, 2, 3, 4};
        cos.write(data, 0, data.length);
        cos.flush();
        String output = baos.toString("US-ASCII");
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xFF);
            if (!output.contains(hex)) {
                throw new AssertionError("Byte array write failed for byte: " + b + ", output: " + output);
            }
        }
        cos.close();
    }
}
