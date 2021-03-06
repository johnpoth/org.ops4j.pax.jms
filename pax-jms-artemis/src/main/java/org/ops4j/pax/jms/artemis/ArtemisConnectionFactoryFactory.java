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
package org.ops4j.pax.jms.artemis;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.apache.activemq.artemis.utils.uri.BeanSupport;
import org.ops4j.pax.jms.service.ConnectionFactoryFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.XAConnection;
import javax.jms.XAConnectionFactory;
import javax.jms.XAJMSContext;
import java.util.HashMap;
import java.util.Map;

public class ArtemisConnectionFactoryFactory implements ConnectionFactoryFactory {

    @Override
    public ConnectionFactory createConnectionFactory(Map<String, Object> props) throws JMSRuntimeException {
        props = new HashMap<>(props);
        String url  = (String) props.remove(ConnectionFactoryFactory.JMS_URL);
        if (url == null) {
            throw new JMSRuntimeException("The url property must be set");
        }
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
        try {
            BeanSupport.setData(cf, props);
        } catch (Exception e) {
            throw (JMSRuntimeException) new JMSRuntimeException("Unable to build Artemis ConnectionFactory").initCause(e);
        }
        return cf;
    }

    @Override
    public XAConnectionFactory createXAConnectionFactory(Map<String, Object> props) throws JMSRuntimeException {
        props = new HashMap<>(props);
        String url  = (String) props.remove(ConnectionFactoryFactory.JMS_URL);
        if (url == null) {
            throw new JMSRuntimeException("The url property must be set");
        }
        ActiveMQXAConnectionFactory xaCf = new ActiveMQXAConnectionFactory(url);
        try {
            BeanSupport.setData(xaCf, props);
        } catch (Exception e) {
            throw (JMSRuntimeException) new JMSRuntimeException("Unable to build Artemis ConnectionFactory").initCause(e);
        }
        return xaCf;
    }

    private void rename(Map<String, Object> props, String oldName, String newName) {
        Object t = props.remove(oldName);
        if (t != null) {
            props.put(newName, t);
        }
    }

}
