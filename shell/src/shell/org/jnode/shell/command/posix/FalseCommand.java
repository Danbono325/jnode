/*
 * $Id: header.txt 2224 2006-01-01 12:49:03Z epr $
 *
 * JNode.org
 * Copyright (C) 2003-2009 JNode.org
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
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
 
package org.jnode.shell.command.posix;

import org.jnode.shell.AbstractCommand;

/**
 * Simply exit with rc of 1; 
 * 
 * @author crawley@jnode.org
 */
public class FalseCommand extends AbstractCommand {

    public FalseCommand() {
        super("Exit with a non-zero return code");
    }

    public static void main(String[] args) throws Exception {
        new FalseCommand().execute(args);
    }

    /**
     * Execute the command
     */
    public void execute() throws Exception {
        exit(1);
    }
}