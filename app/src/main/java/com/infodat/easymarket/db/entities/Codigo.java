package com.infodat.easymarket.db.entities;

/**
 * Created by Home on 10/8/2015.
 */
public class Codigo {
    private String codigo;

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return this.codigo;
    }
}
