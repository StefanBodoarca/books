package com.ro.model;

public class Bid {

    /*
        The item property allows navigation from a Bid to the related Item. This is an association
        with many-to-one multiplicity; users can make many bids for each item.
     */
    private Item item;

    //this is used by Hibernate;
    Bid() {}

    //enforce integrity by requiring an Item argument in the constructor of Bid
    public Bid(Item item) {
        this.item = item;
        item.bids.add(this); // Bidirectional
    }

    public Item getItem() {
        return item;
    }


    //can be commented as we moved setting the Item in the constructor
    //if we remove this setter we need to configure Hibernate to access item field directly
    void setItem(Item item) {
        this.item = item;
    }
}
