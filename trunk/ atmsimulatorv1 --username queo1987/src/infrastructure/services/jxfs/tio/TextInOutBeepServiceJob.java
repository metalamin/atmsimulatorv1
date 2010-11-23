
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;


public class TextInOutBeepServiceJob
    implements IJxfsServiceJob
{

    public TextInOutBeepServiceJob(TextInOutService tioService, int value, int time, int controlId)
    {
        this.cancel = true;
        this.service = tioService;
        this.value = value;
        this.time = time;
        this.controlId = controlId;
    }

    public void execute()
        throws JxfsException
    {
        cancel = false;
        service.beepInternal(this, value, time);
    }

    public boolean cancel()
    {
        return cancel;
    }

    public int getControlId()
    {
        return controlId;
    }

    public int getOperationID()
    {
        return operationId;
    }

    public int getIdentificationID()
    {
        return identificationId;
    }

    public void setIdentificationID(int i)
        throws JxfsException
    {
        identificationId = i;
    }

    private static final int operationId = 8023;
    TextInOutService service;
    int controlId;
    int identificationId;
    boolean cancel;
    int value;
    int time;
}
