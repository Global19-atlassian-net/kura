/*******************************************************************************
 * Copyright (c) 2011, 2020 Eurotech and/or its affiliates and others
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
package org.eclipse.kura.net.admin.modem.sierra.usb598;

import org.eclipse.kura.net.admin.modem.ModemPppConfigGenerator;
import org.eclipse.kura.net.admin.modem.PppPeer;
import org.eclipse.kura.net.admin.visitor.linux.util.ModemXchangeScript;
import org.eclipse.kura.net.modem.ModemConfig;

public class SierraUsb598ConfigGenerator implements ModemPppConfigGenerator {

    @Override
    public PppPeer getPppPeer(String deviceId, ModemConfig modemConfig, String logFile, String connectScript,
            String disconnectScript) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModemXchangeScript getConnectScript(ModemConfig modemConfig) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ModemXchangeScript getDisconnectScript(ModemConfig modemConfig) {
        // TODO Auto-generated method stub
        return null;
    }

}
