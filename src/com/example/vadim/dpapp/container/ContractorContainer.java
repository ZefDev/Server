package com.example.vadim.dpapp.container;

/**
 * Created by Vadim on 12.05.2017.
 */
public class ContractorContainer {
    String name;
    String id;

    public ContractorContainer( String id,String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
