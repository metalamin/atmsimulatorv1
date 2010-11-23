
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;

public class TextInOutSetLedServiceJob
    implements IJxfsServiceJob
{

    public TextInOutSetLedServiceJob(TextInOutService tioService, int i, int j, int k)
    {
        cancel_val_ = true;
        service_ = tioService;
        index_ = i;
        type_ = j;
        control_id_ = k;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        service_.setLEDInternal(this, index_, type_);
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
        return operation_id;
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

    private static final int operation_id = 8025;
    TextInOutService service_;
    int control_id_;
    int identificationID_;
    boolean cancel_val_;
    int index_;
    int type_;
}
