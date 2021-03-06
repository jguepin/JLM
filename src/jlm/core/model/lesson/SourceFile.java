package jlm.core.model.lesson;

import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JScrollPane;

import jlm.core.model.Game;
import jlm.core.model.ProgrammingLanguage;
import jlm.core.ui.JavaEditorPanel;


public class SourceFile {

	protected String name;
	private String template;
	private String body;
	private Map<String, String> patterns;
	private ISourceFileListener listener = null;

	private boolean isCompilable=true;
	private boolean isEditable=true;

	public SourceFile(String name, String initialBody) {
		this(name, initialBody, null, null);
	}

	public SourceFile(String name, String initialBody, String template) {
		this(name, initialBody, template, null);
	}

	public SourceFile(String name, String initialBody, String template, Map<String, String> patterns) {
		this.name = name;
		this.body = initialBody;
		setTemplate( template );
		this.patterns = patterns;
	}

	public String getName() {
		return this.name;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String text) {
		this.body = text;
		notifyListener();
	}
	public void setTemplate(String string) {
		this.template = string;
	}
	public String getTemplate() {
		return template;
	}


	public String getCompilableContent() {
		return getCompilableContent(null);
	}

	public String getCompilableContent(Map<String, String> runtimePatterns) {
		String res;

		if (template != null) {
			res = template.replaceAll("\\$body", this.body+" \n");;			
			if (runtimePatterns != null)
				for (Entry<String, String> pattern : runtimePatterns.entrySet()) {
					res = res.replaceAll(pattern.getKey(), pattern.getValue());
					if (pattern.getValue().contains("\n")) { 
						System.out.println("Damn! I integrated a pattern being more than one line long, line numbers will be wrong. Please repport this bug (alongside with the following informations)!");
						System.out.println("pattern key: "+pattern.getKey());
						System.out.println("pattern value: "+pattern.getValue());
						System.out.println("Exercise: "+Game.getInstance().getCurrentLesson().getCurrentExercise().getName());
						System.out.println("JLM version: "+Game.getProperty("jlm.major.version","internal")+" ("+Game.getProperty("jlm.major.version","internal")+"."+Game.getProperty("jlm.minor.version","")+")");
						System.out.println("Java version: "+System.getProperty("java.version")+" (VM version: "+ System.getProperty("java.vm.version")+")");
						System.out.println("System: " +System.getProperty("os.name")+" (version: "+System.getProperty("os.version")+"; arch: "+ System.getProperty("os.arch")+")");
					}
				}

			if (patterns != null)
				for (String pattern : patterns.keySet()) {
					System.out.println("Replace all "+pattern+" to "+patterns.get(pattern));
					res = res.replaceAll(pattern, patterns.get(pattern));
				}
		} else {
			res = this.body;
		}
		return res.replaceAll("\\xa0", " "); // Kill those damn \160 chars, which are non-breaking spaces (got them from copy/pasting source examples?)
	}

	public void setListener(ISourceFileListener l) {
		this.listener = l;
	}

	public void removeListener() {
		this.listener = null;
	}

	public void notifyListener() {
		if (this.listener != null)
			this.listener.sourceFileContentHasChanged();
	}

	public boolean isCompilable() {
		return isCompilable;
	}

	public void setCompilable(boolean isCompilable) {
		this.isCompilable = isCompilable;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((body == null) ? 0 : body.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SourceFile other = (SourceFile) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		return true;
	}

	public JScrollPane getEditorPanel(ProgrammingLanguage lang) {
		return new JavaEditorPanel(this, lang);
	}
}
