/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author gerar
 */
public class HorarioIncorrecto extends Exception implements ExcepcionesPersonalizadas{

    private String descripcion;

    public HorarioIncorrecto(String mensaje) {
        super(mensaje);
        this.descripcion = "El horario de algún día fue incorrecto";
    }

    public HorarioIncorrecto(String mensaje, String descripcion) {
        super(mensaje);
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
