# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla.

## Yksikkö- ja integraatiotestaus

### sovelluslogiikka

Tärkeimmät testit tapahtuvat Ball-olion testaavassa [BallTest.java] (https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/test/java/BallTest.java) luokassa, sillä pallon liikkuminen on pelin tärkein toiminto.
[GameTest.java] (https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/test/java/GameTest.java) luokassa testataan lähinnä pelin käynnistymistä, kulkua ja päättymistä.

### DAO

[HighScoreTest.java] (https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/test/java/HighScoreTest.java) testaa HighScore-luokkaa joku hakee HighScoreDaon avulla tietoa Google Sheetistä. Sheetin testaaminen vaatii Google-tunnuksen.

### Testauskattavuus

Käyttöliittymää ja Main-luokkaa huomioimatta sovelluksen testauksen rivikattavuus on 96% ja haarautumakattavuus 93%

<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/jacoco.png">>


### Asennus

Sovellusta on testattu [käyttöohjeen](https://github.com/mluukkai/OtmTodoApp/blob/master/dokumentaatio/kayttoohje.md) mukaan Windows ja Linux koneissa.

### Toiminnallisuudet

Kaikki [perustoiminnallisuudet](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md) on testattu.

## Sovellukseen jääneet laatuongelmat

Sovellus vaatii Google tunnuksen taulukon lukua ja kirjoitusta varten.
