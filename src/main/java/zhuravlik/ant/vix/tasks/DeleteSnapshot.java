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

import com.sun.jna.ptr.IntByReference;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import zhuravlik.ant.vix.Vix;
import zhuravlik.ant.vix.VixAction;
import zhuravlik.ant.vix.LibraryHelper;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 09.01.12
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public class DeleteSnapshot extends VixAction {

    String name = null;

    boolean withChildren = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWithChildren() {
        return withChildren;
    }

    public void setWithChildren(boolean withChildren) {
        this.withChildren = withChildren;
    }

    @Override
    public void executeAction(int vmHandle) {
        if (name == null || name.length() == 0) {
            throw new BuildException("Snapshot name is not specified");
        }

        log("Removing snapshot [" + name + "]", Project.MSG_INFO);

        int jobHandle = Vix.VIX_INVALID_HANDLE;
        IntByReference snapshotHandlePtr = new IntByReference();

        int err = LibraryHelper.getInstance().VixVM_GetNamedSnapshot(vmHandle,
                name,
                snapshotHandlePtr);

        jobHandle = LibraryHelper.getInstance().VixVM_RemoveSnapshot(vmHandle,
                snapshotHandlePtr.getValue(),
                withChildren ? Vix.VIX_SNAPSHOT_REMOVE_CHILDREN : 0,
                null,
                null);

        LibraryHelper.getInstance().Vix_ReleaseHandle(snapshotHandlePtr.getValue());

        err = LibraryHelper.getInstance().VixJob_Wait(jobHandle, Vix.VIX_PROPERTY_NONE);
        LibraryHelper.getInstance().Vix_ReleaseHandle(jobHandle);
        checkError(err);
    }
}

