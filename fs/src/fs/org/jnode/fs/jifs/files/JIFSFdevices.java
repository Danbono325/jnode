/*
* $Id$
*
* JNode.org
* Copyright (C) 2005 JNode.org
*
* This library is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as published
* by the Free Software Foundation; either version 2.1 of the License, or
* (at your option) any later version.
*
* This library is distributed in the hope that it will be useful, but 
* WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
* License for more details.
*
* You should have received a copy of the GNU Lesser General Public License 
* along with this library; if not, write to the Free Software Foundation, 
* Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package org.jnode.fs.jifs.files;

import java.util.TreeMap;

import org.jnode.driver.Device;
import org.jnode.driver.DeviceManager;
import org.jnode.fs.FSEntry;
import org.jnode.fs.jifs.JIFSFile;
import org.jnode.naming.InitialNaming;

/**
 * File, which contains information about devices.
 * 
 * @author Andreas H\u00e4nel
 */
public class JIFSFdevices extends JIFSFile{

	
	public JIFSFdevices(String name, FSEntry parent) {
		super(name,parent);
		refresh();
	}
	
	public void refresh(){
		super.refresh();
		final TreeMap<String, Device> tm = new TreeMap<String, Device>();
		try{
			final DeviceManager dm = (DeviceManager) InitialNaming.lookup(DeviceManager.NAME);
			for (Device dev : dm.getDevices()) {
				tm.put(dev.getId(), dev);
			}
		} catch (javax.naming.NameNotFoundException E) {
			System.err.println("could not find DeviceManager");
		}
		for (Device dev : tm.values()) {
			addStringln();
			addString(dev.getId());
			final String drvClassName = dev.getDriverClassName();
			if (dev.isStarted()) {
				addString("\tstarted");
			} else {
				addString("\tstopped");
			}
			if (drvClassName != null) {
				addString("\ndriver: " + drvClassName);
			} else {
				addString("\ndriver: none");
			}
			addStringln();
		}

	}

	

}