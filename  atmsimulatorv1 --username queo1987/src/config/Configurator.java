package config;

import util.GeneralException;

/**
 * Interfaz para los configuradores.
 * Las clases que implementen esta interfaz y deban ser almacenadas
 * para la configuracion posterior DEBEN tener formato JavaBean.
 */
public interface Configurator
{
    //Realiza la configuracion.
    public void configure() throws GeneralException;
}
