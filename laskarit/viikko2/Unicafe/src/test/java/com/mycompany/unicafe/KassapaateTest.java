package com.mycompany.unicafe;

import org.junit.*;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassa = new Kassapaate();
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void alkuTilanneOikein() {
        assertTrue(kassa.kassassaRahaa() == 100000 && kassa.edullisiaLounaitaMyyty() == 0 && kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void edullisestiSyominenToimiiKateisella() {
        kassa.syoEdullisesti(500);
        assertTrue(kassa.kassassaRahaa() == 100240 && kassa.edullisiaLounaitaMyyty() == 1 && kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void maukkaastiSyominenToimiiKateisella() {
        kassa.syoMaukkaasti(700);
        kassa.syoEdullisesti(100);
        kassa.syoMaukkaasti(300);
        assertTrue(kassa.kassassaRahaa() == 100400 && kassa.edullisiaLounaitaMyyty() == 0 && kassa.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void edullisestiSyominenToimiiJosEiTarpeeksiKateista() {
        kassa.syoEdullisesti(100);
        assertTrue(kassa.kassassaRahaa() == 100000 && kassa.edullisiaLounaitaMyyty() == 0 && kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void maukkaastiSyominenToimiiJosEiTarpeeksiKateista() {
        kassa.syoMaukkaasti(300);
        assertTrue(kassa.kassassaRahaa() == 100000 && kassa.edullisiaLounaitaMyyty() == 0 && kassa.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void edullisestiOstoKortillaToimii() {
        assertTrue(kassa.syoEdullisesti(kortti) && kassa.edullisiaLounaitaMyyty() == 1);
    }
    
    @Test
    public void maukkaastiOstoKortillaToimii() {
        assertTrue(kassa.syoMaukkaasti(kortti) && kassa.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void edullisestiOstoKortillaToimiiJosEiTarpeeksiRahaa() {
        kortti.otaRahaa(300);
        assertFalse(kassa.syoEdullisesti(kortti) || kassa.maukkaitaLounaitaMyyty() == 1 || kortti.saldo()!=200);
    }
    
    @Test
    public void maukkaastiOstoKortillaToimiiJosEiTarpeeksiRahaa() {
        kortti.otaRahaa(300);
        assertFalse(kassa.syoMaukkaasti(kortti) || kassa.maukkaitaLounaitaMyyty() == 1 || kortti.saldo()!=200);
    }
    
    @Test
    public void kassanRahatEiMuutuKorttiostoissa() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    
    @Test
    public void kortilleLatausKassallaToimii() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertTrue(kassa.kassassaRahaa() == 100500 && kortti.saldo()==1000);
    }
    
        @Test
    public void kortilleLatausKassallaToimiiJosLadataanNegatiivinenArvo() {
        kassa.lataaRahaaKortille(kortti, -500);
        assertTrue(kassa.kassassaRahaa() == 100000 && kortti.saldo()==500);
    }
}
