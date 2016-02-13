package de.sebikopp.ownjodel.helpers.convert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import de.sebikopp.ownjodel.model.Post;

public class PdfHelper {
	public static byte[] getAsPdf(Post p) throws FOPException, TransformerFactoryConfigurationError, TransformerException{
		ClassLoader cl = PdfHelper.class.getClassLoader();
		InputStream is = cl.getResourceAsStream("props/template.xsl");
		StringWriter sw = new StringWriter();
		JAXB.marshal(p, sw);
		String postAsXml = sw.toString();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Fop fop = FopFactory.newInstance().newFop(MimeConstants.MIME_PDF, baos);
		Source xmlSrc = new StreamSource(new StringReader(postAsXml));
		Source xsl = new StreamSource(is);
		Result sax = new SAXResult(fop.getDefaultHandler());
		Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
		transformer.transform(xmlSrc, sax);
		return baos.toByteArray(); 
		
	}
	
}
