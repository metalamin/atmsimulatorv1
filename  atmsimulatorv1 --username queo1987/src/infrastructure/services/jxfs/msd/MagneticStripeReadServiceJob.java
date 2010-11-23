package infrastructure.services.jxfs.msd;

import com.jxfs.control.msd.JxfsMSDTrackSelection;
import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;
import com.jxfs.service.IJxfsMagStripeService;

public class MagneticStripeReadServiceJob
    implements IJxfsServiceJob
{

    public MagneticStripeReadServiceJob(IJxfsMagStripeService ijxfsmagstripeservice, JxfsMSDTrackSelection jxfsmsdtrackselection, int i)
    {
        cancel_val_ = true;
        service_ = ijxfsmagstripeservice;
        tracksToRead_ = jxfsmsdtrackselection;
        control_id_ = i;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        ((IMagneticStripe)service_).readInternal(this, tracksToRead_);
    }

    public boolean cancel()
    {
        return cancel_val_;
    }

    public int getControlId()
    {
        return control_id_;
    }

    public int getOperationID()
    {
        return 4006;
    }

    public int getIdentificationID()
    {
        return identificationID_;
    }

    public void setIdentificationID(int i)
        throws JxfsException
    {
        identificationID_ = i;
    }

    private static final int operation_id = 4006;
    private IJxfsMagStripeService service_;
    private int control_id_;
    private int identificationID_;
    private boolean cancel_val_;
    private JxfsMSDTrackSelection tracksToRead_;
}
