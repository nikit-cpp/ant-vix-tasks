package zhuravlik.ant.vix.tasks;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import zhuravlik.ant.vix.LibraryHelper;
import zhuravlik.ant.vix.Vix;
import zhuravlik.ant.vix.VixAction;

import com.sun.jna.ptr.IntByReference;

public class GetNamedSnapshot extends VixAction {

    String name = null;

    boolean launchGui = false;
    boolean suppressOn = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLaunchGui() {
        return launchGui;
    }

    public void setLaunchGui(boolean launchGui) {
        this.launchGui = launchGui;
    }

    public boolean isSuppressOn() {
        return suppressOn;
    }

    public void setSuppressOn(boolean suppressOn) {
        this.suppressOn = suppressOn;
    }

    @Override
    public void executeAction(int vmHandle) {
        if (name == null || name.length() == 0) {
            throw new BuildException("Snapshot name is not specified");
        }

        log("Reverting to snapshot [" + name + "]", Project.MSG_INFO);

        //log("VM Handle: " + vmHandle);

        int jobHandle = Vix.VIX_INVALID_HANDLE;
        IntByReference snapshotHandlePtr = new IntByReference();

        int err = LibraryHelper.getInstance().VixVM_GetNamedSnapshot(vmHandle,
            name,
            snapshotHandlePtr);
        checkError(err);

        /*//log("Snapshot handle: " + snapshotHandlePtr.getValue(), Project.MSG_INFO);
        
        int options = 0;
        
        if (suppressOn) options |= Vix.VIX_VMPOWEROP_SUPPRESS_SNAPSHOT_POWERON;
        if (launchGui) options |= Vix.VIX_VMPOWEROP_LAUNCH_GUI;

        jobHandle = LibraryHelper.getInstance().VixVM_RevertToSnapshot(vmHandle,
                snapshotHandlePtr.getValue(),
                options,
                Vix.VIX_INVALID_HANDLE,
                null, null);

        LibraryHelper.getInstance().Vix_ReleaseHandle(snapshotHandlePtr.getValue());

        err = LibraryHelper.getInstance().VixJob_Wait(jobHandle, Vix.VIX_PROPERTY_NONE);*/
        LibraryHelper.getInstance().Vix_ReleaseHandle(jobHandle);
        checkError(err);
    }
}