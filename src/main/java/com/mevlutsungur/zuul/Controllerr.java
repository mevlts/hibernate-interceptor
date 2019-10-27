package com.mevlutsungur.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Controllerr {

    @Autowired
    KisiRepo kisiRepo;

    @GetMapping("guncelle/{id}/{ad}")
    public Kisi guncelle(@PathVariable Long id, @PathVariable String ad) {

          Optional<Kisi> byId = kisiRepo.findById(id);
        Kisi kisi = byId.get();
        kisi.setAd(ad);
        return kisiRepo.save(kisi);

    }
}
