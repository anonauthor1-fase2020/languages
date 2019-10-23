/**
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.technology.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.fhdo.lemma.technology.TechnologyDslRuntimeModule;
import de.fhdo.lemma.technology.TechnologyDslStandaloneSetup;
import de.fhdo.lemma.technology.ide.TechnologyDslIdeModule;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
@SuppressWarnings("all")
public class TechnologyDslIdeSetup extends TechnologyDslStandaloneSetup {
  @Override
  public Injector createInjector() {
    TechnologyDslRuntimeModule _technologyDslRuntimeModule = new TechnologyDslRuntimeModule();
    TechnologyDslIdeModule _technologyDslIdeModule = new TechnologyDslIdeModule();
    return Guice.createInjector(Modules2.mixin(_technologyDslRuntimeModule, _technologyDslIdeModule));
  }
}
