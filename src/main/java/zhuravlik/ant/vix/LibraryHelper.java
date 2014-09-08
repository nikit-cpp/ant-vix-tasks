/*
   Copyright (C) 2012-2013 Anton Lobov <zhuravlik> <ahmad200512[at]yandex.ru>

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General
   Public License along with this library; if not, write to the
   Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301 USA
 */

package zhuravlik.ant.vix;

import com.sun.jna.Native;
import org.apache.commons.lang.SystemUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import java.io.File;

/**
 * LibraryHelper is the helper class that automatically sets VIX library path
 * and loads VIX library using JNA
 *
 * On Windows platform
 */

public class LibraryHelper {


    public static Vix instance = null;
    public static String path = null;
    public static String provider = null;
    
    public static Vix getInstance() {
        if (instance == null) {
            instance = loadVixLibrary();            
        }
        
        return instance;
    }
    
    public static Vix loadVixLibrary() {
        if (new File("/usr/lib/vmware-vix/libvixAllProducts.so").exists())     // on Unix there is single entry point
            return (Vix) Native.loadLibrary("vixAllProducts", Vix.class);
        else
            // return (Vix) Native.loadLibrary("vix", Vix.class);     // on Windows there is vix.dll
            return (Vix) Native.loadLibrary(path, Vix.class);     // on Windows there is vix.dll
    }
}
