/*
 * generated by Xtext 2.16.0
 */
package de.fhdo.lemma.data.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class DataDslAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("de/fhdo/lemma/data/parser/antlr/internal/InternalDataDsl.tokens");
	}
}
