/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

/**
 *
 * @author fran
 */
public class DniType {
    
    private int id;
    private String name;

    public DniType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DniType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
