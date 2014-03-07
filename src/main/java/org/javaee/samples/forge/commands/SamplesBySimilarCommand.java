package org.javaee.samples.forge.commands;

import java.io.PrintStream;
import java.util.List;

import javax.inject.Inject;

import org.javaee.samples.forge.IndexReader;
import org.javaee.samples.forge.Sample;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.ui.command.UICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

public class SamplesBySimilarCommand extends AbstractProjectCommand implements UICommand {

	@Inject
	private ProjectFactory projectFactory;

	@Inject
	@WithAttributes(shortName = 's', label = "source", required = true, type = InputType.JAVA_CLASS_PICKER)
	private UIInput<JavaResource> source;

	@Inject
	private IndexReader index;

	@Inject
	private OutputFormatter formatter;

	@Inject
	private ImportScanner scanner;
	
	@Override
	public UICommandMetadata getMetadata(UIContext context) {
		return Metadata.from(super.getMetadata(context), getClass())
				.category(Categories.create("JavaEE Samples"))
				.name("samples-by-similar").description("Search for samples based on content from a given java class");
	}

	@Override
	public void initializeUI(UIBuilder builder) throws Exception {
		builder.add(source);
	}

	@Override
	public Result execute(UIExecutionContext context) throws Exception {
		PrintStream out = context.getUIContext().getProvider().getOutput().out();
		
		List<String> found = scanner.scan(source.getValue());
		if(found.size() == 0) {
			return Results.fail("No known categories found in source file");
		}

		List<Sample> results = index.search(IndexReader.byUsage(found));
		formatter.output(out, results);

		return Results.success("Found total of " + results.size() + " matching " + found);
	}

	@Override
	protected boolean isProjectRequired() {
		return true;
	}

	@Override
	protected ProjectFactory getProjectFactory() {
		return projectFactory;
	}
}
