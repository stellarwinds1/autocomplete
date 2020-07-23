package com.mycompany.autocomplete;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Test;
import org.junit.BeforeClass;
import java.util.List;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
{
	static AutoComplete ac; 

	@BeforeClass
	public static void oneTimeSetup() throws Exception {
		ac = new AutoComplete();
	}

    @Test
    public void testAddValidDictionaryEntry()
    {
		try {
			String[] qryArray = {"def"};

			ac.addToDictionary(qryArray);

			String qry = "def";
			List<String> returnList = ac.query(qry);
			System.out.println("For query \"" + qry + "\" and no limit, the returnList is " + returnList);

		} catch (Exception e) {
			fail("Caught an exception " + e.getMessage() + " incorrectly");
		}
    }

    @Test
    public void testExistingEntry()
    {
		try {
			List<String> returnList;
			List<String> expectedList;
			String qry;
			int limit = -1;

			qry = "a";
			returnList = ac.query(qry);
			System.out.println("For query \"" + qry + "\" and no limit, the returnList is " + returnList);
			expectedList = Arrays.asList("aaa", "aab", "aac", "aba", "aca");
			assertThat(returnList, is(expectedList));

			qry = "a";
			limit = 2;
			returnList = ac.query(qry, limit);
			System.out.println("For query \"" + qry + "\" and limit " + limit + " the returnList is " + returnList);
			expectedList = Arrays.asList("aaa", "aab");
			assertThat(returnList, is(expectedList));

			qry = "ab";
			returnList = ac.query(qry);
			System.out.println("For query \"" + qry + "\" and no limit, the returnList is " + returnList);

			qry = "abc";
			returnList = ac.query(qry);
			System.out.println("For query \"" + qry + "\" and no limit, the returnList is " + returnList);

			qry = "def";
			returnList = ac.query(qry);
			System.out.println("For query \"" + qry + "\" and no limit, the returnList is " + returnList);

		} catch (Exception e) {
			fail("Caught an exception " + e.getMessage() + " incorrectly");
		}
    }

    @Test
    public void testBlankEntry()
    {
		try {
			List<String> returnList;
			String qry;

			qry = "";
			returnList = ac.query(qry);
			fail("Did not fail for the query with an empty string ");
		} catch (Exception e) {
			System.err.println("Caught an empty string exception " + e.getMessage() + " correctly");
		}
    }

    @Test
    public void testAddBlankDictionaryEntry()
    {
		try {
			String[] qryArray = {"def", ""};

			ac.addToDictionary(qryArray);
			fail("Did not fail while adding the empty value to the dictionary ");
		} catch (Exception e) {
			System.err.println("Caught an empty string addition " + e.getMessage() + " correctly");
		}
    }

    @Test
    public void testNullEntry()
    {
		try {
			List<String> returnList;
			String qry;

			qry = null;
			returnList = ac.query(qry);
			fail("Did not fail for the query with a null  string ");
		} catch (Exception e) {
			System.err.println("Caught a null string exception " + e.getMessage() + " correctly");
		}
    }
}
