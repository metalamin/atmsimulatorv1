
package infrastructure.services.jxfs.msd;

import com.jxfs.control.msd.JxfsMSDTrackSelection;

public interface IMagneticStripe
{

    public abstract void writeInternal(MagneticStripeWriteServiceJob writeservicejob, String s, String s1, String s2);

    public abstract void readInternal(MagneticStripeReadServiceJob readservicejob, JxfsMSDTrackSelection jxfsmsdtrackselection);

}
