/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.ops4j.pax.jms.pool.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;

import java.util.Dictionary;
import java.util.Hashtable;

public class Activator implements BundleActivator {

    private static final String FACTORY_PID = "org.ops4j.connectionfactory";
    private ConnectionFactoryConfigManager configManager;
    private ServiceRegistration<ManagedServiceFactory> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        Dictionary<String, String> props = new Hashtable<>();
        props.put(Constants.SERVICE_PID, FACTORY_PID);
        configManager = new ConnectionFactoryConfigManager(context);
        registration = context.registerService(ManagedServiceFactory.class, configManager, props);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
    }

}
