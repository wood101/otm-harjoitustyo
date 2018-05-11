# Arkkitehtuurikuvaus

Koodin pakkausrakenne noudattaa seuraavaa kaaviota:
<br>
<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/pakkauskaavio.png">
<br>
- [fi.helsinki.arkanoidotm.graphics](https://github.com/wood101/otm-harjoitustyo/tree/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/graphics) sisältää grafiikat piirtävät metodit.
- [fi.helsinki.arkanoidotm.game](https://github.com/wood101/otm-harjoitustyo/tree/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game) luo muut osat pelistä ja käynnistää sen.
- [fi.helsinki.arkanoidotm.game.components](https://github.com/wood101/otm-harjoitustyo/tree/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/components) sisältää pelikentän muuttuvat osat.
- [fi.helsinki.arkanoidotm.game.highscore](https://github.com/wood101/otm-harjoitustyo/tree/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/highscore) sisältää pisteitä muuttavat metodit ja pistetaulukon tallennuksesta vastuussa olevan koodin.       

## Käyttöliittymä

Käyttöliittymä on piirretty javan Graphics oliolla, joka piirtyy aina uudestaan asioiden muuttuessa tai liikkuessa. Grafiikka-olio luodaan [Game](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/Game.java) luokassa, jonka jälkeen Grafiikat piirtävää luokkaa [GameGraphics](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/graphics/GameGraphics.java) kutsutaan.

## Sovelluslogiikka

Sovelluksen toiminta tapahtuu lähinnä luokissa [Game](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/Game.java) ja pakkausen [components](https://github.com/wood101/otm-harjoitustyo/tree/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/components) luokissa. Game luokassa pyörii [Thread](https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html), joka kutsuu [Ball-luokkaa](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/components/Ball.java), joka puolestaan kutsuu muita komponentteja tarvittaessa.

Pallo voi:
- [Lautaan](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/components/Board.java) osuessaan kimmota
- [Palikkaan](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/components/Block.java)  osuessaan tuhota sen ja kimmota siitä
- Reunoihin osuessaan kimmota tai alareunan tapauksessa menettää elämän ja tilanne resetoituu.

### Luokkakaavio
Pelin ja sen komponenttien suhdetta kuvaava luokkakaavio:
<br>
<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/luokkakaavio.png">

### Sekvenssikaavio
Muutamia olennaisimpia toiminnallisuuksia kuvaava sekvenssikaavio, liittyen lähinnä pallon toimintaan:
<br>
<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavio.png">
<br>
Pallon osuessa alareunaan menettää elämän, josta häviää kun elämät loppuu.
Pallon osuessa palikkaan palikoiden määrä vähenee, joka johtaa pelin voittamiseen kun ne ovat loppu.

## Tietojen pysyväistallennus

HighScore-taulukko on tallennettu vapaasti muokattavissa olevaan Google Sheettiin, josta sovellus lukee ja jonne sovellus kirjoittaa. Tietojen luku ja kirjoitus löytyy [HighScoreDao.java](https://github.com/wood101/otm-harjoitustyo/blob/master/ArkanoidOTM/src/main/java/fi/helsinki/arkanoidotm/game/highscore/HighScoreDao.java) luokasta.
[Sheetissä](https://docs.google.com/spreadsheets/d/1QQmmAWKtWSMejc_26vOyew0qZg_niVJ9I0AAVfF9tuE/edit?usp=sharing) on skripti, joka järjestää aina muutoksen jälkeen tulokset suuruusjärjestykseen.


## Ohjelman rakenteeseen jääneet heikkoudet

### Käyttöliittymä

Tiedon tallennus ja luku vaatii Google käyttäjän. Vaikka tämä tapahtuukin vain jos voittaa pelin ja silloinkin sen voi sivuuttaa, niin se on turha välivaihe. Sheetin skripti ei järjestä taulukkoa tarpeeksi nopeasti, uusi rivi näkyy aina huonoimpana arvona.

### Sovelluslogiikka

Pallon kimpoaminen ei ole täysin realistista.
