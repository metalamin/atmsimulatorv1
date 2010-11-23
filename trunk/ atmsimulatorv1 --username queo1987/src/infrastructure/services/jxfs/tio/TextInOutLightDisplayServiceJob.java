
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;

public class TextInOutLightDisplayServiceJob
    implements IJxfsServiceJob
{

    public TextInOutLightDisplayServiceJob(TextInOutService tioService, boolean flag, int i)
    {
        cancel_val_ = true;
        service_ = tioService;
        on_ = flag;
        control_id_ = i;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        service_.lightDisplayInternal(this, on_);
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

    private static final int operation_id = 8024;
    TextInOutService service_;
    int control_id_;
    int identificationID_;
    boolean cancel_val_;
    boolean on_;
}
