package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikein() {
        assertEquals("saldo: 0.10", kortti.toString());      
    }
    
    @Test
    public void lataaminenOikein() {
        kortti.lataaRahaa(50);
        assertEquals("saldo: 0.60", kortti.toString());      
    }
    
    @Test
    public void ottoToimii() {
        kortti.otaRahaa(5);
        assertTrue(kortti.saldo() == 5);
    }
    
        @Test
    public void eiVoiOttaaLiikaa() {
        kortti.otaRahaa(11);
        assertTrue(kortti.saldo() == 10);
    }
    
}
