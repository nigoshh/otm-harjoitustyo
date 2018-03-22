package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(500);
        assertEquals("saldo: 15.0", kortti.toString());
    }

    @Test
    public void rahaVaheneeOikeinJosRahaaOnTarpeeksi() {
        kortti.otaRahaa(300);
        assertEquals("saldo: 7.0", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(1010);
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittivat() {
        assertTrue(kortti.otaRahaa(400));
    }

    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiittaneet() {
        assertFalse(kortti.otaRahaa(1400));
    }

    @Test
    public void saldoPalauttaaOikeanSaldon() {
        assertEquals(1000, kortti.saldo());
    }
}
