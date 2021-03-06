/*******************************************************************************
 * Copyright (c) 2020 Eurotech and/or its affiliates and others
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  Eurotech
 *******************************************************************************/

package org.eclipse.kura.net.admin.modem.telefonica;

import org.eclipse.kura.net.admin.NetworkConfigurationService;
import org.eclipse.kura.net.admin.util.AbstractCellularModemFactory;
import org.eclipse.kura.net.modem.ModemDevice;
import org.eclipse.kura.net.modem.ModemTechnologyType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.io.ConnectionFactory;
import org.osgi.util.tracker.ServiceTracker;

public class TelefonicaModemFactory extends AbstractCellularModemFactory<TelefonicaModem> {
    
    private static TelefonicaModemFactory factoryInstance = null;
    private ConnectionFactory connectionFactory = null;

    private TelefonicaModemFactory() {
        final BundleContext bundleContext = FrameworkUtil.getBundle(NetworkConfigurationService.class)
                .getBundleContext();

        ServiceTracker<ConnectionFactory, ConnectionFactory> serviceTracker = new ServiceTracker<>(bundleContext,
                ConnectionFactory.class, null);
        serviceTracker.open(true);
        this.connectionFactory = serviceTracker.getService();
    }

    @Override
    protected TelefonicaModem createCellularModem(ModemDevice modemDevice, String platform) throws Exception {
        return new TelefonicaModem(modemDevice, platform, this.connectionFactory);
    }

    public static TelefonicaModemFactory getInstance() {
        if (factoryInstance == null) {
            factoryInstance = new TelefonicaModemFactory();
        }
        return factoryInstance;
    }

    @Override
    public ModemTechnologyType getType() {
        return ModemTechnologyType.LTE;
    }
}
