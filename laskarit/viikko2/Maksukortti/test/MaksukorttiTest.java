import org.junit.*;
import static org.junit.Assert.*;

public class MaksukorttiTest {
    
    Maksukortti kortti;
    
    @Before
    public void SetUp() {
    kortti = new Maksukortti(10);
    }
    
    @Test
public void konstruktoriAsettaaSaldonOikein() {
    assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
}

@Test
public void syoEdullisestiVahentaaSaldoaOikein() {
    kortti.syoEdullisesti();
    assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
}

@Test
public void syoMaukkaastiVahentaaSaldoaOikein() {
    kortti.syoMaukkaasti();
    assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
}

@Test
public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
    kortti.syoMaukkaasti();
    kortti.syoMaukkaasti();
    kortti.syoEdullisesti();
    assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
}  

@Test
public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
    kortti.syoMaukkaasti();
    kortti.syoMaukkaasti();
    kortti.syoMaukkaasti();
    assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
}  

@Test
public void kortilleVoiLadataRahaa() {
    kortti.lataaRahaa(25);
    assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
}

@Test
public void eiVoiLadataNegatiivistaArvoa() {
    kortti.lataaRahaa(-10);
    assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
}

@Test
public void voiSyodaEdullisestiMinimiRahalla() {
    kortti.syoEdullisesti();
    kortti.syoEdullisesti();
    kortti.syoEdullisesti();
    kortti.syoEdullisesti();
    assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
}

@Test
public void voiSyodaMaukkaastiMinimiRahalla() {
    kortti.lataaRahaa(2);
    kortti.syoMaukkaasti();
    kortti.syoMaukkaasti();
    kortti.syoMaukkaasti();
    assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
}

@Test
public void kortinSaldoEiYlitaMaksimiarvoa() {
    kortti.lataaRahaa(200);
    assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
}
}
