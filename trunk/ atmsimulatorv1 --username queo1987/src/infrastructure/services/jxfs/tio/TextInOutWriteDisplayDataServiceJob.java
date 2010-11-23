
package infrastructure.services.jxfs.tio;

import com.jxfs.events.JxfsException;
import com.jxfs.forum.support.IJxfsServiceJob;

public class TextInOutWriteDisplayDataServiceJob
    implements IJxfsServiceJob
{

    public TextInOutWriteDisplayDataServiceJob(TextInOutService textInOutService, int i, int j, int k, int l, String s, int i1)
    {
        cancel_val_ = true;
        service_ = textInOutService;
        mode_ = i;
        posX_ = j;
        posY_ = k;
        textAttr_ = l;
        text_ = s;
        control_id_ = i1;
    }

    public void execute()
        throws JxfsException
    {
        cancel_val_ = false;
        service_.writeDisplayDataInternal(this, mode_, posX_, posY_, textAttr_, text_);
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
        return 8027;
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

    private static final int operation_id = 8027;
    TextInOutService service_;
    int control_id_;
    int identificationID_;
    boolean cancel_val_;
    int mode_;
    int posX_;
    int posY_;
    int textAttr_;
    String text_;
}
