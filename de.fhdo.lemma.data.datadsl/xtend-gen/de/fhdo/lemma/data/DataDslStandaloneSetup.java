/**
 * generated by Xtext 2.12.0
 */
package de.fhdo.lemma.data;

import de.fhdo.lemma.data.DataDslStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class DataDslStandaloneSetup extends DataDslStandaloneSetupGenerated {
  public static void doSetup() {
    new DataDslStandaloneSetup().createInjectorAndDoEMFRegistration();
  }
}
