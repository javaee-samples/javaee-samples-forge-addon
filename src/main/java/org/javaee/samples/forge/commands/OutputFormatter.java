package org.javaee.samples.forge.commands;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.javaee.samples.forge.Sample;

public class OutputFormatter {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public void output(PrintStream out, List<Sample> results) {
		for(Sample sample: results) {
			out.println("Name:    " + sample.getName());
			out.println("Updated: " + format.format(sample.getLastUpdated()) + "\t" + sample.getUsage());
			out.println("         " + sample.getDescription());
			out.println("         " + sample.getHtmlURL());
			out.println();
		}
	}
}
