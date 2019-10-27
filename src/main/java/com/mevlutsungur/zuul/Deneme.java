package com.mevlutsungur.zuul;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class Deneme {

    @Autowired
    KisiRepo kisiRepo;
    @Autowired
    EntityManager entityManager;

    @PostConstruct
    public void denee() {
        Kisi kisi = Kisi.builder().ad("Mevlüt").soyad("Sungur").id(1L).build();
        Kisi kisi2 = Kisi.builder().ad("Mevlüt2").soyad("Sungur2").id(2L).build();

        kisiRepo.saveAll(Arrays.asList(kisi,kisi2));



        Kisi byId = kisiRepo.findById(1L).get();
        System.out.println(byId);


        byId.setSoyad("SUNAR");
        kisiRepo.save(byId);

        System.out.println(kisiRepo.findById(1L)+ " " +  kisiRepo.findById(1L));
    }
}
