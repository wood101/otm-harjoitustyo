# Arkanoid-peli OTM-harjoitustyö

Pelissä on tarkoitus kimmottaa palloa laudalla esteisiin, jotka hajoavat törmäyksestä.

## Dokumentaatio
[Vaatimusmäärittely](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
<br>
[Työaikakirjanpito](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)
<br>
[Arkkitehtuuri](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
<br>
[Käyttöohje](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
<br>
[Testausdokumentti](http://github.com/wood101/otm-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)

## Releaset

[Viikko 5](https://github.com/wood101/otm-harjoitustyo/releases/tag/viikko5)
<br>
[Viikko 6](https://github.com/wood101/otm-harjoitustyo/releases/tag/viikko6)
<br>
[Lopullinen](https://github.com/wood101/otm-harjoitustyo/releases/tag/final)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _OtmTodoApp-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
