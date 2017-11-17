package org.orbisgis.orbisserver.api;

import org.orbisgis.orbisserver.api.service.ServiceFactory;

/**
 * Interface for the BaseServer class which is the base of the orbisserver.
 * It contains all the ServiceFactory and is used to manage the session.
 *
 * @author Sylvain PALOMINOS
 */
public interface BaseServer {

    /**
     * Register a ServiceFactory which will be used to instantiate a Service on a Session creation.
     * @param serviceFactory ServiceFactory to register.
     */
    void registerServiceFactory(ServiceFactory serviceFactory);

    /**
     * Unregister a ServiceFactory and shutdown all the instantiate services with the factory
     * @param serviceFactory
     */
    void unregisterServiceFactory(ServiceFactory serviceFactory);
}
