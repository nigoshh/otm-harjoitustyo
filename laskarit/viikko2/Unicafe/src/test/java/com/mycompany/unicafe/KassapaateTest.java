package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassa;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }

    @Test
    public void luodunKassapaatteenRahamaaraOnOikea() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void luodunKassapaatteenLounaidenMaaraOnOikea() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josEdullinenKateisMaksuRiittaaOikeaRahamaaraJaVaihtoraha() {
        assertEquals(260, kassa.syoEdullisesti(500));
        assertEquals(100240, kassa.kassassaRahaa());
    }

    @Test
    public void josMaukkaanKateisMaksuRiittaaOikeaRahamaaraJaVaihtoraha() {
        assertEquals(600, kassa.syoMaukkaasti(1000));
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void josEdullinenKateisMaksuRiittaaMyytyjenLounaidenMaaraKasvaa() {
        kassa.syoEdullisesti(500);
        kassa.syoEdullisesti(300);
        kassa.syoEdullisesti(240);
        assertEquals(3, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josMaukkaanKateisMaksuRiittaaMyytyjenLounaidenMaaraKasvaa() {
        kassa.syoMaukkaasti(1000);
        kassa.syoMaukkaasti(400);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josEdullinenKateisMaksuEiRiitaKaikkiToimiiKuitenkin() {
        assertEquals(230, kassa.syoEdullisesti(230));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josMaukkaanKateisMaksuEiRiitaKaikkiToimiiKuitenkin() {
        assertEquals(390, kassa.syoMaukkaasti(390));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKortillaRahaaSummaVeloitetaanSyoEdullisestiPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(2000);
        assertTrue(kassa.syoEdullisesti(kortti));
        assertEquals(1760, kortti.saldo());
    }

    @Test
    public void josKortillaRahaaSummaVeloitetaanSyoMaukkaastiPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(3000);
        assertTrue(kassa.syoMaukkaasti(kortti));
        assertEquals(2600, kortti.saldo());
    }

    @Test
    public void josKortillaRahaaEdullistenMyytyjenLounaidenMaaraKasvaa() {
        Maksukortti kortti = new Maksukortti(480);
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void josKortillaRahaaMaukkaidenMyytyjenLounaidenMaaraKasvaa() {
        Maksukortti kortti = new Maksukortti(1200);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(3, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void josKortillaEiRiitaRahaaEdulLounaidenJaRahaEiMuutuJaRetFalse() {
        Maksukortti kortti = new Maksukortti(120);
        assertFalse(kassa.syoEdullisesti(kortti));
        assertEquals(120, kortti.saldo());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());

    }

    @Test
    public void josKortillaEiRiitaRahaaMaukLounaidenJaRahaEiMuutuJaRetFalse() {
        Maksukortti kortti = new Maksukortti(270);
        assertFalse(kassa.syoMaukkaasti(kortti));
        assertEquals(270, kortti.saldo());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kortillaOstettaessaEdullisenLounaanKassanRahaEiMuutu() {
        Maksukortti kortti = new Maksukortti(270);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kortillaOstettaessaMaukkaanLounaanKassanRahaEiMuutu() {
        Maksukortti kortti = new Maksukortti(470);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kortinLatausToimii() {
        Maksukortti kortti = new Maksukortti(500);
        kassa.lataaRahaaKortille(kortti, 1800);
        assertEquals(2300, kortti.saldo());
        assertEquals(101800, kassa.kassassaRahaa());
    }

    @Test
    public void kortilleEiVoiLadataNegatiivistaSummaa() {
        Maksukortti kortti = new Maksukortti(500);
        kassa.lataaRahaaKortille(kortti, -400);
        assertEquals(500, kortti.saldo());
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
