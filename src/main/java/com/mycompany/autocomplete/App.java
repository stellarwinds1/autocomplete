package com.mycompany.autocomplete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

class AutoComplete {
	public static List<String> dictionary = null;

	public AutoComplete() {

		if (dictionary == null) {
			dictionary = new ArrayList<String>();
			dictionary.add("aaa");
			dictionary.add("aab");
			dictionary.add("aac");
			dictionary.add("aba");
			dictionary.add("aca");
			dictionary.add("baa");
			dictionary.add("caa");
		}
	}

	public List<String> query(String qry) throws Exception {
		return query(qry, -1);
	}

	public List<String> query(String qry, int sizeLimit) throws Exception {
		List<String> returnList = new ArrayList<String>();

		if (qry == null || qry.equals("")) {
			throw new Exception("empty query ");
		}
		for (int i = 0; i < dictionary.size(); i++) {
			String dictionaryEntry = dictionary.get(i);
			if (dictionaryEntry.substring(0, qry.length()).equals(qry)) {
				returnList.add(dictionaryEntry);
				if ((sizeLimit > 0)  && (returnList.size() == sizeLimit)) {
					break;
				}
			}
		}

		return returnList;
	}

	public void addToDictionary(String[] queryEntries) throws Exception {
		if (queryEntries == null | queryEntries.length == 0) {
			throw new Exception ("Empty query entry list cannot be added to the dictionary");
		}

		for (int i = 0; i < queryEntries.length; i++) {
			String queryEntry = queryEntries[i];

			if (queryEntry.equals("")) {
				throw new Exception ("Cannot add a blank entry to the dictionary");
			}
	
			if (!dictionary.contains(queryEntry)) {
				dictionary.add(queryEntry);
			} else {
				// Do NOT duplicate
				;
			}
		}
	}

}

@SpringBootApplication
@RestController
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

		AutoComplete ac = new AutoComplete();
		try {
			ac.query("a");
			ac.query("ab");
			ac.query("abc");
			ac.query("def");
		} catch (Exception e) {
			System.err.println("Caught an exception " + e.getMessage());
		}
	}

	@GetMapping("/autocomplete")
	public String autocomplete(@RequestParam(value = "q", defaultValue = "") String qry, @RequestParam(value = "n", defaultValue = "-1") int sizeLimit) {
		AutoComplete ac = new AutoComplete();
		try {
			return "" + ac.query(qry, sizeLimit);
		} catch (Exception e) {
			return "Caught an exception " + e.getMessage();
		}

	}

	@PostMapping("/dictionary")
	public List<String> dictionary(@RequestBody String[] dictEntries) {
		
		AutoComplete ac = new AutoComplete();
		try {
			ac.addToDictionary(dictEntries);
		} catch (Exception e) {
			System.err.println("Caught an exception " + e.getMessage());
		}

		return AutoComplete.dictionary;

	}
}
