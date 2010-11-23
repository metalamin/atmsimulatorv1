
package infrastructure.services.jxfs.msd;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;
import com.jxfs.service.IJxfsMagStripeService;

public class MagneticStripeWriteServiceJob
    implements IJxfsServiceJob
{

    public MagneticStripeWriteServiceJob(IJxfsMagStripeService ijxfsmagstripeservice, String s, String s1, String s2, int i)
    {
        cancel_val_ = true;
        service_ = ijxfsmagstripeservice;
        track1_ = s;
        track2_ = s1;
        track3_ = s2;
        control_id_ = i;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        ((IMagneticStripe)service_).writeInternal(this, track1_, track2_, track3_);
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
        return 4008;
    }

    public int getIdentificationID()
    {
        return identification_id_;
    }

    public void setIdentificationID(int i)
        throws JxfsException
    {
        identification_id_ = i;
    }

    private static final int operation_id_ = 4008;
    private IJxfsMagStripeService service_;
    private int control_id_;
    private int identification_id_;
    private boolean cancel_val_;
    private String track1_;
    private String track2_;
    private String track3_;
}
