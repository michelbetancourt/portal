package portal.core.modules.form;

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

public class FormModule implements IModule {

	private StringTemplateGroup formGroup = new StringTemplateGroup("form");

	private final String _formSubmitName;

	private final String _formSubmitValue;

	public FormModule(String formSubmitName, String formSubmitValue) {
		_formSubmitName = formSubmitName;
		_formSubmitValue = formSubmitValue;
	}

	@Override
	public void doModule(ModuleContext context) {

		PrintWriter out = context.getOutputWriter();
		StringTemplate st = formGroup.getInstanceOf(TemplateTools
				.getSTTemplatePath(getClass(), "form"));
		List<FormInput<Object>> values = new ArrayList<FormInput<Object>>();
		values.add(new FormInput<Object>("blah", "blahvalue"));
		st.setAttribute("elements", values);
		out.append(st.toString());

	}

	protected boolean isFormSubmitted(ModuleContext context) {
		String formValue = context.getRequest().getParameter(_formSubmitName);
		boolean isSubmitted;
		if (!StringTools.isEmpty(formValue)) {
			isSubmitted = formValue.equals(_formSubmitValue);
		} else {
			isSubmitted = false;
		}

		return isSubmitted;
	}

	protected void processForm(List<FormInput<Object>> formInput) {

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
				.convertPackageToSlashString(FormModule.class)
				+ "/form");
		System.out.println("QUERY: " + st.toString());

	}
}
