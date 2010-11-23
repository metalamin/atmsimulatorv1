
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;

public class TextInOutClearScreenServiceJob
    implements IJxfsServiceJob
{

    public TextInOutClearScreenServiceJob(TextInOutService tioService, int i, int j, int k, int l, int i1)
    {
        cancel_val_ = true;
        service_ = tioService;
        positionX_ = i;
        positionY_ = j;
        width_ = k;
        height_ = l;
        control_id_ = i1;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        service_.clearScreenInternal(this, positionX_, positionY_, width_, height_);
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

    private static final int operation_id = 8026;
    TextInOutService service_;
    int control_id_;
    int identificationID_;
    boolean cancel_val_;
    int positionX_;
    int positionY_;
    int width_;
    int height_;
}
