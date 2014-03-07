package org.javaee.samples.forge.commands;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.parser.java.Import;

public class ImportScanner {

	private Map<String, String[]> categories = new HashMap<>();
	{
		categories.put("WebSocket", new String[] {"javax.websocket"});
		categories.put("Batch", new String[] {"javax.batch"});
		categories.put("Concurrency", new String[] {"javax.enterprise.concurrent"});
		categories.put("JSON", new String[] {"javax.json"});
		categories.put("JAXRS", new String[] {"javax.ws.rs"});
		categories.put("JMS", new String[] {"javax.jms"});
		categories.put("CDI", new String[] {"javax.inject", "javax.enterprise"});
		categories.put("EJB", new String[] {"javax.ejb"});
		categories.put("EL", new String[] {"javax.el"});
		categories.put("JASPIC", new String[] {"javax.security.auth"});
		categories.put("JavaMail", new String[] {"javax.mail"});
		categories.put("JCA", new String[] {"javax.resource"});
		categories.put("JPA", new String[] {"javax.persistence"});
		categories.put("JSF", new String[] {"javax.faces"});
		categories.put("JTA", new String[] {"javax.transaction"});
		categories.put("Servlet", new String[] {"javax.servlet"});
		categories.put("validation", new String[] {"javax.validation"});
		categories.put("jaxws", new String[] {"javax.xml.ws", "javax.jws"});
	}
	
	
	
	public List<String> scan(JavaResource resource) throws FileNotFoundException {
		Set<String> result = new HashSet<>();
		
		for(Import imp : resource.getJavaSource().getImports()) {
			for(Map.Entry<String, String[]> entry : categories.entrySet()) {
				for(String pack : entry.getValue()) {
					if(imp.getPackage().startsWith(pack)) {
						result.add(entry.getKey());
					}
				}
			}
		}
		return new ArrayList<>(result);
	}
}
