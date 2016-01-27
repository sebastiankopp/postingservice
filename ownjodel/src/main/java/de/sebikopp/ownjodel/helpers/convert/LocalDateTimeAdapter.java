package de.sebikopp.ownjodel.helpers.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ISO_DATE_TIME;
	@Override
	public LocalDateTime unmarshal(String v) throws Exception {
		return LocalDateTime.parse(v, DATE_TIME_FORMAT);
	}

	@Override
	public String marshal(LocalDateTime v) throws Exception {
		return v.format(DATE_TIME_FORMAT);
	}

}
