package triagramTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import triagram.TextParser;

public class ParserTest {

	TextParser textParser = null;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		textParser = new TextParser();
	}

	@Test
	public void testNullInputToParseFile() {
		try {
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("Please, provide a valid file name.");
			textParser.parseFile(null);
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testBlankInputToParseFile() {
		try {
			exception.expect(IllegalArgumentException.class);
			exception.expectMessage("Please, provide a valid file name.");
			textParser.parseFile("");
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testNonExistingFileToParseFile() {
		try {
			exception.expect(IllegalArgumentException.class);
			textParser.parseFile("nonExistingFile.txt");
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testEmptyFileToParseFile() {
		try {
			exception.expect(IllegalArgumentException.class);
			textParser.parseFile("C:\\Kata\\testfiles\\Empty.txt");
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testValidFileToParseFile() {
		try {
			assertEquals(8,
					textParser.parseFile("C:\\Kata\\testfiles\\validFile.txt")
							.size());
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testPunctuationInParseFile() {
		try {
			assertEquals(
					8,
					textParser.parseFile(
							"C:\\Kata\\testfiles\\punctuationTest.txt").size());
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testLargeFileInParseFile() {
		try {
			List<String> wordList = textParser
					.parseFile("C:\\Kata\\testfiles\\largeFileFromProjectGutenberg.txt");
			assertNotNull(wordList);
			assertEquals("The", wordList.get(0));
			String last = wordList.get(wordList.size()-1);
			assertEquals("eBooks", last);
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

	@Test
	public void testPopulateTriagramNullInput() {
		exception.expect(IllegalArgumentException.class);
		exception
				.expectMessage("Please, provide a valid list that is not null.");
		textParser.populateTriagrams(null);
	}

	@Test
	public void testPopulateTriagramListSizeLesstThanThree() {
		exception.expect(IllegalArgumentException.class);
		exception
				.expectMessage("Please, provide a valid list that has atleast 3 elements to generate Triagram.");
		@SuppressWarnings("serial")
		List<String> list = new ArrayList<String>() {
			{
				add("A");
				add("B");
			}
		};
		textParser.populateTriagrams(list);
	}

	@Test
	public void testPopulateTriagram() {
		try {
			List<String> wordList = textParser
					.parseFile("C:\\Kata\\testfiles\\punctuationTest.txt");
			assertEquals(4, textParser.populateTriagrams(wordList).size());
		} catch (IOException e) {
			fail("Scanner threw IOException: " + e.getMessage());
		}
	}

}
