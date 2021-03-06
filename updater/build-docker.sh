#!/bin/bash

# Do gradle build
cd ..

echo "# Setting version"
rm src/main/resources/maggdaforestdefense/config/version.txt
date +"Version %y%m%d-%H%M%S (alpha)" > src/main/resources/maggdaforestdefense/config/version.txt

echo "# Building"
./gradlew build --no-daemon

mkdir updater/tmp
mkdir updater/ForestDefense2

echo 'Copying stuff'
cp build/distributions/ForestDefense-app.zip updater/tmp/ForestDefense-app.zip
cp build/libs/ForestDefense-app.jar updater/ForestDefense2/ForestDefense.jar
# shellcheck disable=SC2164
cd updater/
cd tmp/
unzip ForestDefense-app.zip
# shellcheck disable=SC2103
cd ..
cp -r tmp/ForestDefense-app/lib ForestDefense
cp -r tmp/ForestDefense-app/bin ForestDefense
#cp tmp/ForestDefense-app/lib/ForestDefense-app.jar ForestDefense/ForestDefense-app.jar

echo '# Skipping generating digest'
# java -classpath getdown-core-1.8.6.jar com.threerings.getdown.tools.Digester ForestDefense

echo '# Generating open source licenses'
cp ../OPENSOURCELICENSES.md ./ForestDefense2/OPENSOURCELICENSES.md
pandoc ./../OPENSOURCELICENSES.md -f markdown -t html -s --metadata title="OPEN SOURCE LICENSES" --toc -H ./opensourcelicenses/style.html -A ./opensourcelicenses/footer.html -o ./web/opensourcelicenses.html

echo "# Skipping generating update"
# shellcheck disable=SC2164
cp -r ForestDefense ./web/updater/ForestDefense

echo "# Generating ienokihpkg update"

rm -rf ForestDefense
mkdir ForestDefense

wget -O openjfx.zip https://gluonhq.com/download/javafx-15-0-1-sdk-windows/

unzip openjfx.zip
mv javafx-*/bin/* ForestDefense2

mv ForestDefense2/* ForestDefense

wget https://ienokih.pages.minortom.net/ienokihpkg-lockfilegenerator/lockfilegenerator
cd ForestDefense || exit
chmod +x ../lockfilegenerator
../lockfilegenerator
cd .. || exit

cp -r ForestDefense ./web/ienokihpkg