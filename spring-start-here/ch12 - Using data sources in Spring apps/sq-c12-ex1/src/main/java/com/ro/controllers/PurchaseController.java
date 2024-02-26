package com.ro.controllers;

import com.ro.model.Purchase;
import com.ro.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    @PostMapping
    public void storePurchase(@RequestBody Purchase purchase) {
        purchaseRepository.storePurchase(purchase);
    }

    @GetMapping
    public List<Purchase> findPurchases() {
        return purchaseRepository.findAllPurchases();
    }


}
