/*
 * generated by Xtext 2.16.0
 */
package de.fhdo.lemma.technology.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class TechnologyDslAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("de/fhdo/lemma/technology/parser/antlr/internal/InternalTechnologyDsl.tokens");
	}
}
