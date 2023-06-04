## Readme - M3

* Gruppe: 2
* Team-Nr.:	6
* Projektthema: Reiseplaner

### Implementierung

Framework: Android

API-Version: Android API-Level 27

Gerät(e), auf dem(denen) getestet wurde:
Pixel 6

Externe Libraries und Frameworks:
dependencies {

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}

Dauer der Entwicklung:
ca. 160 Stunden

Weitere Anmerkungen:
Da es sich bei unserer App um einen High-Fidelity Prototypen handelt sind sämtliche Daten über die Reiserouten, welche anschließend als Ergebnis ausgegeben werden, hartkodiert. Es gibt nur eine begrenzte Anzahl an Städten, welche für die Routenfindung zur Auswahl stehen und der User muss diese aus der vorgeschlagenen Liste auswählen. Bei dem Ticketkauf wird beispielhaft auf die Website eines passenden Anbieters verlinkt, wo der tatsächliche Ticketerwerb stattfinden könnte.
