/*
20221016, VN
Projekti hajosi johonkin ubuntuvirheeseen. Activity.java ei löydä importteja etc . . . Pljon punaista.
./gradlew check kertoi, että

/home/pappa/AndroidStudioProjects/petrol/app/build/intermediates/merged_manifest/release/AndroidManifest.xml': No such file or directory
Koko release hakis puuttui.

pappa@pappa-ThinkPad-X270:~/AndroidStudioProjects/android-basics-kotlin-lemonade-app-main/app/build/intermediates/merged_manifests$ scp -pr release/ /home/pappa/AndroidStudioProjects/petrol/app/build/intermediates/merged_manifest/
Ei auttanut.

pappa@pappa-ThinkPad-X270:~/AndroidStudioProjects/petrol$ ./gradlew build

> Task :app:compileReleaseKotlin
w: /home/pappa/AndroidStudioProjects/petrol/app/src/main/java/com/viware/petrol/MainActivity.kt: (38, 17): 'startActivityForResult(Intent!, Int): Unit' is deprecated. Deprecated in Java
w: /home/pappa/AndroidStudioProjects/petrol/app/src/main/java/com/viware/petrol/MainActivity.kt: (51, 15): 'onActivityResult(Int, Int, Intent?): Unit' is deprecated. Overrides deprecated member in 'androidx.activity.ComponentActivity'. Deprecated in Java

> Task :app:lintReportDebug
Wrote HTML report to file:///home/pappa/AndroidStudioProjects/petrol/app/build/reports/lint-results-debug.html

BUILD SUCCESSFUL in 47s
94 actionable tasks: 60 executed, 34 up-to-date
pappa@pappa-ThinkPad-X270:~/AndroidStudioProjects/petrol$
Meni läpi ja yllätys yllätys, studiossa projekti toimii taas!

Chipmunk kuva ei tule näkyviin studion käynnistyksessä, on vaan pelkät raamit.
Komentoriviltä käynnistäessä (studio.sh) tulee näkyviin ok.
20221026, VN: Thinkpadin bootin jälkeen chipmunk tuli sitten näkyviin työpöydälä käynnistettynäkin.
 */