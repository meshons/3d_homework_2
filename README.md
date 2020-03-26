# Space Ship Game

### Rövid leírás

A játékos egy úrhajót irányít, ami képes lézert lőni, 
amivel az űrben lévő kisebb és nagyobb meteorokat robbanthatja fel.
 Az űrhajó mindig a képernyő közepén található.

### Részletesebb leírás

##### Űrhajó

* Mindig az egérmutató felé néz.
* A "SPACE" billentyű lenyomásával gyorsulni képes a saját irányában. 
(van maximális sebessége)
* Ha van éppen mozog, de nincs lenyomva a "SPACE" billentyű, akkor lassulni kezd,
míg meg nem áll
* Ha kicsi vagy nagy meteorral ütközik, akkor a meteor felrobban (és el is tűnik).
* Az egér balklikkelésével vagy a "B" billentyű benyomásával lézert lő magából 
a saját irányába. Fontos, hogy van egy megadott lehűlési idő, amin belül nem lőhet
újabb lézert.

##### Meteor

* Lassan forog maga körül.
* Ha lézerrel, meteorral vagy űrhajóval ütközik felrobban és eltűnik.

##### Lézer

* Fix sebességgel halad a kilövés irányában.
* Megadott idő után eltűnik.
* Ha bármilyen meteorral ütközik eltűnik.

##### Robbanás

* Nem tud ütközni semmivel.
* Megadott idő után eltűnik.

### megvalósított funkciók:

1. Avatar
    * Az űrhajót képes a játékos irányítani (sebessége, iránya)
 
2. Lövedék
    * Lézer, amit egérkattintással vagy billentyűlenyomással lehet indítani és 
    van lehűlési időszaka.

3. Ütközés
    * Körlapos ütközésdetektálás a meteornál, lézernél.
    * Lézer ha ütközik csak eltűnik.
    * Meteor ha ütközik felrobban.

4. Kamera
    * Folyamatosan követi az űrhajót.
    * Két rétegű űr témájú háttér, ahol az alsó réteg lassabban mozog.

5. Robbanás
    * Animált robbanás.
    * Meteor tud felrobbanni ütközés során.
