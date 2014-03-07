package org.javaee.samples.forge.commands;

import java.io.PrintStream;
import java.util.List;

import javax.inject.Inject;

import org.javaee.samples.forge.IndexReader;
import org.javaee.samples.forge.Sample;
import org.jboss.forge.addon.convert.Converter;
import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class SamplesByCategoryCommand extends AbstractUICommand implements UICommand {

	@Inject
	@WithAttributes(shortName = 'c', label = "category", required = true)
	private UISelectOne<String> category;

	@Inject
	private IndexReader index;

	@Inject
	private OutputFormatter formatter;

	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.from(super.getMetadata(context), getClass())
				.category(Categories.create("JavaEE Samples"))
				.name("samples-by-category").description("Search for samples by a given category");
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {

		category.setValueChoices(index.categories());
		category.setItemLabelConverter(new Converter<String, String>() {
			@Override
			public String convert(String source) {
				return source.toLowerCase();
			}
		});
		builder.add(category);
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		PrintStream out = context.getUIContext().getProvider().getOutput().out();
		
		List<Sample> results = index.search(IndexReader.byCategory(category.getValue()));
		formatter.output(out, results);

		return Results.success("Found total of " + results.size() + " matches");
	}
}
