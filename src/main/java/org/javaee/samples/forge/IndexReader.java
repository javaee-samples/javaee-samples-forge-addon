package org.javaee.samples.forge;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class IndexReader {

	public List<Sample> search(Matcher matcher) {
		List<Sample> result = new ArrayList<>();
		for(Sample sample : all()) {
			if(matcher.match(sample)) {
				result.add(sample);
			}
		}
		Collections.sort(result, matcher);
		return result;
	}
	
	public List<Sample> all() {
		return parse();
	}
	
	public List<String> categories() {
		Set<String> unique = new HashSet<>();
		for(Sample sample : all()) {
			unique.add(sample.category);
		}
		return new ArrayList<String>(unique);
	}
	
	
	@SuppressWarnings("serial")
	private List<Sample> parse() {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
		try {
			return gson.fromJson(
					new JsonReader(
							new InputStreamReader(
									getClass().getResource("index.json").openStream())),
					new TypeToken<List<Sample>>() {}.getType());
		} catch(IOException io) {
			throw new RuntimeException("Could not parse local index", io);
		}
	}
	
	public interface Matcher extends Comparator<Sample> {
		boolean match(Sample sample);
	}
	
	public static Matcher byCategory(final String category) {
		if(category == null) {
			throw new IllegalArgumentException("Category must be specified");
		}
		return new Matcher() {
			
			@Override
			public boolean match(Sample sample) {
				return category.equalsIgnoreCase(sample.category);
			}
			
			@Override
			public int compare(Sample o1, Sample o2) {
				return 0;
			}
		};
	}

	public static Matcher byUsage(final List<String> categories) {
		if(categories == null || categories.size() == 0) {
			throw new IllegalArgumentException("Category must be specified");
		}
		return new Matcher() {
			
			@Override
			public boolean match(Sample sample) {
				if(sample.usage == null) {
					return false;
				}
				for(String usage : sample.usage) {
					for(String category : categories) {
						return category.equalsIgnoreCase(usage);
					}
				}
				return false;
			}

			@Override
			public int compare(Sample o1, Sample o2) {
				return matchCount(o1, categories).compareTo(matchCount(o2, categories));
			}

			private Integer matchCount(Sample sample, List<String> categories) {
				int count = 0;
				for(String usage : sample.usage) {
					for(String category : categories) {
						if(category.equalsIgnoreCase(usage)) {
							count++;
						}
					}
				}
				return count;
			}
		};
	}
}
