package de.sebikopp.ownjodel.helpers.convert;

public class StringConverter {
	public static String escapeHtml(String src){
		return src.replace("<", "&lt;").replace(">", "&gt;");
	}
}
