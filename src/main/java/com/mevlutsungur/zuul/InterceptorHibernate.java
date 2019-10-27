package com.mevlutsungur.zuul;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Auditable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Slf4j
public class InterceptorHibernate extends EmptyInterceptor {

    private int updates;
    private int creates;
    private int loads;



    public void onDelete(Object entity,
                         Serializable id,
                         Object[] state,
                         String[] propertyNames,
                         Type[] types) {
        // do nothing
    }

    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        System.out.println(entity);
        System.out.println(id);
        System.out.println(currentState);
        System.out.println(previousState);
        System.out.println(types);
        return null;
    }


    public boolean onFlushDirty(Object entity,
                                Serializable id,
                                Object[] currentState,
                                Object[] previousState,
                                String[] propertyNames,
                                Type[] types) {
        KisiRepo kisiRepo = (KisiRepo ) ApplicationContextProvider.getApplicationContext().getBean("kisiRepo");
        log.info("Güncelleme yapılıyor");
        if(entity instanceof Kisi) {
            for(int i=0;i<propertyNames.length;i++) {
                System.out.println(propertyNames[i]);
            }
            for(int i=0;i<previousState.length;i++) {
                System.out.println(previousState[i] + " " + currentState[i]);
                kisiRepo.save(Kisi.builder().ad(i + " kisi değişti").id(i+10L).build());
            }

        }

        return false;
    }

    public boolean onLoad(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {
        if ( entity instanceof Auditable ) {
            loads++;
        }
        return false;
    }

    public boolean onSave(Object entity,
                          Serializable id,
                          Object[] state,
                          String[] propertyNames,
                          Type[] types) {

        if ( entity instanceof Auditable ) {
            creates++;
            for ( int i=0; i<propertyNames.length; i++ ) {
                if ( "createTimestamp".equals( propertyNames[i] ) ) {
                    state[i] = new Date();
                    return true;
                }
            }
        }
        return false;
    }

    public void afterTransactionCompletion(Transaction tx) {
//        if ( tx.wasCommitted() ) {
////            System.out.println("Creations: " + creates + ", Updates: " + updates, "Loads: " + loads);
////        }
        updates=0;
        creates=0;
        loads=0;
    }

}