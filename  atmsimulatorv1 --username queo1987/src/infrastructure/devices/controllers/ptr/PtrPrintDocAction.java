package infrastructure.devices.controllers.ptr;

import util.GeneralException;
import infrastructure.devices.DeviceHandlerFactory;
import domain.state.Action;
import domain.state.Event;
import infrastructure.devices.events.DeviceException;

/**
 * Action de imprimir un texto en la impresora
 */
public class PtrPrintDocAction implements Action
{
    private String deviceName;
    private String data;
    private String[] labels;
    private String[] dataKeys;
    
    public PtrPrintDocAction()
    {
    }

    public PtrPrintDocAction(String deviceName) 
    {
        this.setDeviceName(deviceName);
    }

    public PtrPrintDocAction(String deviceName, String[] labels, String[] dataKeys) 
    {
        this.setDeviceName(deviceName);
        this.setLabels(labels);
        this.setDataKeys(dataKeys);
    }
    
    public PtrPrintDocAction(String deviceName, String data) 
    {
        this.setDeviceName(deviceName);
        this.setData(data);
    }
    
    public void update(Event ev) throws GeneralException
    {
        IPtrController ptrController = (IPtrController)DeviceHandlerFactory.getDeviceHandler().getDeviceManager().getDevice(getDeviceName());
        
        if (data == null){
            if (getLabels() != null && getDataKeys() != null){
                data = "";
                for (int i=0; i<getLabels().length; i++){
                    // save label
                    data += getLabels()[i];
                    // save data is not null
                    String dataItem = (String)ev.getContext().get(getDataKeys()[i]);
                    if (dataItem != null){
                        data += ": " + dataItem;
                    }
                    // save end of line
                    data += "\n";
                }            
            }
            setLabels(null);
            setDataKeys(null);
        }
        
        if (data == null){
            data = (String)ev.getContext().get(IPtrConst.PTR_DATA);
        }
        
        String[] dataBuffer = {data};
        try{
            ptrController.printForm("PrintForm", "DocForm",dataBuffer);
            data = null;
        }
        catch(DeviceException e){
            data = null;
            throw new GeneralException(e);
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String[] getDataKeys() {
        return dataKeys;
    }

    public void setDataKeys(String[] dataKeys) {
        this.dataKeys = dataKeys;
    }


}
