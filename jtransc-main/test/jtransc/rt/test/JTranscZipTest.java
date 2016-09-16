package jtransc.rt.test;

import com.jtransc.io.JTranscIoTools;
import com.jtransc.util.JTranscFiles;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JTranscZipTest {
	static public void main(String[] args) throws IOException {
		testFs();
		testZip();
	}

	static private void testFs() {
		System.out.println("FS:");
		System.out.println(new File("__nonExistantFile__").exists());
		System.out.println(normalizePath(new File("__nonExistantFile__").getAbsolutePath()));
	}

	static private String normalizePath(String path) {
		return path.replace('\\', '/');
	}

	static private void testZip() throws IOException {
		System.out.println("ZIP:");
		char[] hexDataChar = new char[]{
			0x50, 0x4B, 0x03, 0x04, 0x0A, 0x03, 0x00, 0x00, 0x00, 0x00, 0x49, 0x9E, 0x74, 0x48, 0xA3, 0x1C,
			0x29, 0x1C, 0x0C, 0x00, 0x00, 0x00, 0x0C, 0x00, 0x00, 0x00, 0x09, 0x00, 0x00, 0x00, 0x68, 0x65,
			0x6C, 0x6C, 0x6F, 0x2E, 0x74, 0x78, 0x74, 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x20, 0x57, 0x6F, 0x72,
			0x6C, 0x64, 0x21, 0x50, 0x4B, 0x03, 0x04, 0x14, 0x03, 0x00, 0x00, 0x08, 0x00, 0x35, 0xB5, 0x74,
			0x48, 0xAA, 0xC0, 0x69, 0x3A, 0x1D, 0x00, 0x00, 0x00, 0x38, 0x07, 0x00, 0x00, 0x0A, 0x00, 0x00,
			0x00, 0x68, 0x65, 0x6C, 0x6C, 0x6F, 0x32, 0x2E, 0x74, 0x78, 0x74, 0xF3, 0x48, 0xCD, 0xC9, 0xC9,
			0x57, 0x08, 0xCF, 0x2F, 0xCA, 0x49, 0x51, 0x1C, 0x65, 0x8F, 0xB2, 0x47, 0xD9, 0xA3, 0xEC, 0x51,
			0xF6, 0x28, 0x7B, 0x94, 0x8D, 0x9F, 0x0D, 0x00, 0x50, 0x4B, 0x01, 0x02, 0x3F, 0x03, 0x0A, 0x03,
			0x00, 0x00, 0x00, 0x00, 0x49, 0x9E, 0x74, 0x48, 0xA3, 0x1C, 0x29, 0x1C, 0x0C, 0x00, 0x00, 0x00,
			0x0C, 0x00, 0x00, 0x00, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x80,
			0xA4, 0x81, 0x00, 0x00, 0x00, 0x00, 0x68, 0x65, 0x6C, 0x6C, 0x6F, 0x2E, 0x74, 0x78, 0x74, 0x50,
			0x4B, 0x01, 0x02, 0x3F, 0x03, 0x14, 0x03, 0x00, 0x00, 0x08, 0x00, 0x35, 0xB5, 0x74, 0x48, 0xAA,
			0xC0, 0x69, 0x3A, 0x1D, 0x00, 0x00, 0x00, 0x38, 0x07, 0x00, 0x00, 0x0A, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x80, 0xA4, 0x81, 0x33, 0x00, 0x00, 0x00, 0x68, 0x65, 0x6C,
			0x6C, 0x6F, 0x32, 0x2E, 0x74, 0x78, 0x74, 0x50, 0x4B, 0x05, 0x06, 0x00, 0x00, 0x00, 0x00, 0x02,
			0x00, 0x02, 0x00, 0x6F, 0x00, 0x00, 0x00, 0x78, 0x00, 0x00, 0x00, 0x00, 0x00
		};
		byte[] hexData = new byte[hexDataChar.length];
		for (int n = 0; n < hexDataChar.length; n++) hexData[n] = (byte) hexDataChar[n];

		System.out.println(hexData.length);

		System.out.println("[1]");
		String tmpdir = System.getProperty("java.io.tmpdir");
		System.out.println("[2]");
		System.out.println(tmpdir);
		String tmpfile = tmpdir + "/jtransc.test.zip";
		System.out.println("[3]");
		System.out.println(tmpfile);
		JTranscFiles.write(new File(tmpfile), hexData);
		System.out.println("[4]");

		System.out.println(normalizePath(new File(tmpfile).getParentFile().getAbsolutePath()));
		//System.out.println(normalizePath(new File(tmpfile).getParentFile().getParent()));

		for (File file : new File[]{
			new File(tmpdir + "/__non_existant_file__"),
			new File(tmpfile),
			new File(tmpfile.replace('\\', '/')),
			new File(tmpdir)
		}) {
			System.out.println("file:" + normalizePath(file.getAbsolutePath()));
			System.out.println("exists:" + file.exists());
			System.out.println("isFile:" + file.isFile());
			System.out.println("isDirectory:" + file.isDirectory());
			System.out.println("canRead:" + file.canRead());
			System.out.println("canWrite:" + file.canWrite());
			System.out.println("length:" + (file.isFile() ? file.length() : 0));
		}

		RandomAccessFile r = new RandomAccessFile(tmpfile, "r");
		System.out.println("read:");
		System.out.println(r.readShort());
		System.out.println(r.readInt());
		r.close();
		ZipFile zipFile = new ZipFile(tmpfile);
		System.out.println(normalizePath(zipFile.getName()));
		System.out.println(zipFile.getComment());
		System.out.println(zipFile.getEntry("hello.txt"));
		for (ZipEntry entry : Collections.list(zipFile.entries())) {
			System.out.println(entry.getName() + " : " + entry.getSize() + " : " + entry.getCompressedSize());
			System.out.println(new String(JTranscIoTools.readStreamFully(zipFile.getInputStream(entry)), "utf-8"));
		}
	}
}

