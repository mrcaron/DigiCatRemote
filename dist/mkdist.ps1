gci const-lib -filter "*.jar" | foreach { jar -xvf $_.FullName }
jar -xvf ..\App\target\DigiCatLive*.jar
jar -xvf ..\Net\target\NetLib*.jar

jar cvfm DigiCatLive.jar Manifest.txt com.intelix.digihdmiapp.DigiHdmiApp com net org
