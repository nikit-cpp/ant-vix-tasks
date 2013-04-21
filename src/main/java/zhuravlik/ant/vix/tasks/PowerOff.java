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

package zhuravlik.ant.vix.tasks;

import org.apache.tools.ant.Project;
import zhuravlik.ant.vix.Vix;
import zhuravlik.ant.vix.VixAction;
import zhuravlik.ant.vix.LibraryHelper;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 09.01.12
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class PowerOff extends VixAction {

    String mode = "normal";

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public void executeAction(int vmHandle) {
        log("Sending powerOff command", Project.MSG_INFO);

        if (!mode.equals("normal") && !mode.equals("from_guest")) {
            log("Unknown mode: " + mode + ", assumed normal", Project.MSG_WARN);
        }

        int jobHandle = Vix.VIX_INVALID_HANDLE;

        jobHandle = LibraryHelper.getInstance().VixVM_PowerOff(vmHandle,
                mode.equals("normal") ? Vix.VIX_VMPOWEROP_NORMAL : Vix.VIX_VMPOWEROP_FROM_GUEST,
                null, null);

        int err = LibraryHelper.getInstance().VixJob_Wait(jobHandle, Vix.VIX_PROPERTY_NONE);
        LibraryHelper.getInstance().Vix_ReleaseHandle(jobHandle);
        checkError(err);
    }
}