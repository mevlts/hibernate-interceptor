package com.mevlutsungur.zuul;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KisiRepo extends JpaRepository<Kisi, Long> {
}
