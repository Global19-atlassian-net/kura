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
package org.eclipse.kura.core.deployment.xml;

public class XmlDeploymentPackage {

    private String name;

    private String version;

    private XmlBundleInfo[] bundleInfos;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public XmlBundleInfo[] getBundleInfos() {
        return this.bundleInfos;
    }

    public void setBundleInfos(XmlBundleInfo[] bundleInfos) {
        this.bundleInfos = bundleInfos;
    }
}
