package com.example.techcorp.events;

import com.example.techcorp.domain.Company;

public class MarketSlowdownEvent implements GameEvent {
    @Override
    public void apply(Company company) {
        System.out.println("WYDARZENIE: Spowolnienie na rynku! Tracisz 5000 kapitału.");
        company.reduceCash(5000);
    }
}