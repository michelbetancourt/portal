package portal.core.modules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import portal.core.IModule;
import portal.core.ModuleContext;
import portal.core.html.form.FormInput;
import portal.core.tools.StringTools;
import portal.core.tools.TemplateTools;

public class AddModule implements IModule {

	StringTemplateGroup group = new StringTemplateGroup("mygroup");

	public AddModule() {

	}

	@Override
	public void doModule(ModuleContext container) {

		PrintWriter out = container.getOutputWriter();
		StringTemplate st = group.getInstanceOf(TemplateTools
				.getSTTemplatePath(getClass(), "form"));
		List<FormInput<Object>> values = new ArrayList<FormInput<Object>>();
		values.add(new FormInput<Object>("blah", "blahvalue"));
		st.setAttribute("elements", values);
		out.append(st.toString());

	}

	public static void main(String[] args) {
		StringTemplate query = new StringTemplate(
				"SELECT $column$ FROM $table$;");
		query.setAttribute("column", "subject");
		query.setAttribute("table", "emails");
		System.out.println("QUERY: " + query.toString());

		// Look for templates in CLASSPATH as resources
		StringTemplateGroup group = new StringTemplateGroup("mygroup");

		StringTemplate st = group.getInstanceOf(StringTools
				.convertPackageToSlashString(AddModule.class)
				+ "/form");
		System.out.println("QUERY: " + st.toString());

	}
}
