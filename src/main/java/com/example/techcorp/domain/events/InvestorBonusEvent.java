package com.example.techcorp.events;

import com.example.techcorp.domain.Company;

public class InvestorBonusEvent implements GameEvent {
    @Override
    public void apply(Company company) {
        System.out.println("WYDARZENIE: Sukces na konferencji! Inwestor przekazuje 10000 do budżetu.");
        company.addCash(10000);
    }
}