package org.javaee.samples.forge;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IndexReaderTestCase {

	private IndexReader reader;
	
	@Before
	public void createReader() {
		reader = new IndexReader();
	}
	
	@Test
	public void shouldBeAbleToReadIndex() throws Exception {
		
		Assert.assertTrue(reader.all().size() > 0);
	}

	@Test
	public void shouldBeAbleToFilterIndex() throws Exception {
		
		List<Sample> all = reader.all();
		List<Sample> filtered = reader.search(IndexReader.byCategory("CDI"));
		
		//System.out.println(filtered);
		
		Assert.assertTrue(all.size() > filtered.size());
		Assert.assertTrue(filtered.size() > 0);
	}
	
	@Test
	public void shouldBeAbleToReadAllCategories() throws Exception {
		
		List<String> categories = reader.categories();
		//System.out.println(categories);
		
		Assert.assertTrue(categories.size() > 0);
	}

	@Test
	public void shouldBeAbleToFilterByUsage() throws Exception {
		
		List<Sample> categories = reader.search(IndexReader.byUsage(Arrays.asList("CDI", "SERVLET")));
		//System.out.println(categories);
		
		Assert.assertTrue(categories.size() > 0);
	}
}