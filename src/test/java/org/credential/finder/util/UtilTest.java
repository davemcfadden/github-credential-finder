package org.credential.finder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.egit.github.core.Repository;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	private Repository repo;

	private String validFilePath;

	private String filePath;

	private String dummyFileName;

	@Before
	public void setup() {
		repo = new Repository();
		repo.setHtmlUrl("https://github.com/davemcfadden/github-credential-finder");
		validFilePath = "src/test/resources/example.file";
		filePath = "src/main/java/org/credential/finder/Application.java";
		dummyFileName = "dummyFile.txt";
		createDummyFile();
	}

	@Test
	public void testGetHazardStrings() {
		assertEquals("password", Util.getHazardStrings().get(3));
	}

	@Test
	public void testRepositoryUserContentUrl() {
		List<String> filePaths = new ArrayList<String>();
		filePaths.add(filePath);
		List<String> reponseList = Util.repositoryUserContentUrl(repo, "master", filePaths);
		for (String response : reponseList) {
			assertEquals("https://raw.githubusercontent.com/davemcfadden/github-credential-finder/master/" + filePath,
					response);
		}

	}

	@Test
	public void testStreamFileInvalidPath() {
		assertEquals(0, Util.streamFile("not/real/path").size());
	}

	@Test
	public void testStreamFileValidPath() {
		assertEquals(17, Util.streamFile(validFilePath).size());
		assertEquals("password", Util.streamFile(validFilePath).get(3));
	}

	@Test
	public void testFileCleanUp() {
		// set up a dummy file as it will be deleted each time
		String dummyFilePath = "src/test/resources/" + dummyFileName;
		Util.fileCleanUp(Arrays.asList(dummyFilePath));
		File f = new File(dummyFilePath);
		assertTrue(!f.exists());
	}

	private void createDummyFile() {
		String dummyFilePath = "src/test/resources/dummyFile.txt";
		try {
			Files.write(Paths.get(dummyFilePath), Arrays.asList("dummyFile"), Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
