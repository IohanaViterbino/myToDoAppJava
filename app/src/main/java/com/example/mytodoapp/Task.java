package com.example.mytodoapp;

public class Task {
    private Integer Id;
    private String Texto;
    private boolean isChecked;

    public Integer getId() {
        return Id;
    }

    public String getTexto() {
        return Texto;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setId(Integer id) {
        Id = id;
    }
    public void setTexto(String texto) {
        Texto = texto;
    }

    public void setIsChecked(Boolean Checked) {
        isChecked = Checked;
    }
}