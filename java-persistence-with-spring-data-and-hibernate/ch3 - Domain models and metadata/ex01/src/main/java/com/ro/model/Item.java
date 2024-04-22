package com.ro.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Item {

    /*
        This association between the two classes allows for bidirectional navigation: the many-to-one
        is from this perspective a one-to-many multiplicity.
     */
    Set<Bid> bids = new HashSet<>();

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    public void addBid(Bid bid) {
        if (bid == null)
            throw new NullPointerException("Can't add null Bid");

        if (bid.getItem() != null)
            throw new IllegalStateException("Bid is already assigned to an Item");

        bids.add(bid);
        bid.setItem(this);
    }
}
