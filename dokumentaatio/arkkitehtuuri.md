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

### Luokkakaavio
<br>
<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/luokkakaavio.png">

### Sekvenssikaavio
Muutamia olennaisimpia toiminnallisuuksia kuvaava sekvenssikaavio:
<br>
<img src="https://raw.githubusercontent.com/wood101/otm-harjoitustyo/master/dokumentaatio/kuvat/sekvenssikaavio.png">
<br>
Pallo liikkuessaan, voi tuhota esteitä ja vaihtaa suuntaa osuessaan asioihin. Osuessaan kentän alareunaan pelaaja menettää yhden elämän.
