/*
 * Copyright 2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.internal.xjc.reader.dtd.bindinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.sun.tools.internal.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.internal.xjc.model.CClassInfoParent;
import com.sun.tools.internal.xjc.model.CEnumConstant;
import com.sun.tools.internal.xjc.model.CEnumLeafInfo;
import com.sun.tools.internal.xjc.model.Model;
import com.sun.tools.internal.xjc.model.TypeUse;

import org.w3c.dom.Element;

/**
 * &lt;enumeration> declaration in the binding file.
 */
public final class BIEnumeration implements BIConversion
{
    /** Creates an object from &lt;enumeration> declaration. */
    private BIEnumeration( Element _e, TypeUse _xducer ) {
        this.e = _e;
        this.xducer = _xducer;
    }
    
    /** &lt;enumeration> element in DOM. */
    private final Element e;
    
    private final TypeUse xducer;
    
    public String name() { return DOMUtil.getAttribute(e,"name"); }
    
    /** Returns a transducer for this enumeration declaration. */
    public TypeUse getTransducer() { return xducer; }
    
    
    
    
    /** Creates a global enumeration declaration. */
    static BIEnumeration create( Element dom, BindInfo parent ) {
        // create a class in the target package.
        return new BIEnumeration(
            dom,
            new CEnumLeafInfo(
                parent.model,
                null,
                new CClassInfoParent.Package(parent.getTargetPackage()),
                DOMUtil.getAttribute(dom,"name"),
                CBuiltinLeafInfo.STRING,
                buildMemberList(parent.model,dom),
                null, null/*TODO*/,
                DOM4JLocator.getLocationInfo(dom)));
    }
    
    /** Creates an element-local enumeration declaration. */
    static BIEnumeration create( Element dom, BIElement parent ) {
        // create a class as a nested class
        return new BIEnumeration(
            dom,
            new CEnumLeafInfo(
                parent.parent.model,
                null,
                parent.clazz,
                DOMUtil.getAttribute(dom,"name"),
                CBuiltinLeafInfo.STRING,
                buildMemberList(parent.parent.model,dom),
                null, null/*TODO*/,
                DOM4JLocator.getLocationInfo(dom) ));
    }
    
    private static List<CEnumConstant> buildMemberList( Model model, Element dom ) {
        List<CEnumConstant> r = new ArrayList<CEnumConstant>();

        String members = DOMUtil.getAttribute(dom,"members");
        if(members==null) members="";   // TODO: error handling
        
        StringTokenizer tokens = new StringTokenizer(members);
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            r.add(new CEnumConstant(model.getNameConverter().toConstantName(token),
                    null,token,null));
        }
        
        return r;
    }
}