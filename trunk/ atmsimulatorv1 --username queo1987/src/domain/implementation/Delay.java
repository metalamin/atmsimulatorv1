package domain.implementation;

import domain.state.Action;
import domain.state.Event;

/**
 * Esta clase ejecuta un "Delay" de
 * tanto tiempo.
 * 
 * El tiempo viene dado por:
 *		- El numero de milisegundos.
 *		- El numero de unidades de tiempo,
 *		  con su conversion a milisegundos.
 */
 
public class Delay implements Action 
{
    // El tiempo, expresado en unidades...
    private long tiempo_espera;

    // Las unidades de conversion...
    private int conversion_unit;

    public Delay()
    {
        this(0);
    }
    
    public Delay(long milliseconds)
    {
        this(milliseconds, 1);
    }

    public Delay(long time, int conversion)
    {
        setWaitTime(time);
        setConversionUnit(conversion);
    }

    /**
     * El update espera el tiempo indicado...
     * si es interrumpido, vuelve limpiamente.
     */	
    public void update(Event event) 
    {
        try
        {
            Thread.currentThread().sleep(getWaitTime() * getConversionUnit());
        }
        catch (InterruptedException ex)
        {
            return;
        }
    }

    public Long getWaitTime() {
        return new Long(tiempo_espera);
    }

    public void setWaitTime(Long tiempo_espera) {
        this.tiempo_espera = tiempo_espera.longValue();
    }

    public int getConversionUnit() {
        return conversion_unit;
    }

    public void setConversionUnit(Integer conversion_unit) {
        this.conversion_unit = conversion_unit.intValue();
    }
    
    public String toString()
    {
        return "Delay";
    }
}
