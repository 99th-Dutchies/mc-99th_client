@Echo off

Rem %1 = base minecraft version : 1.16.5
Rem %2 = name .jar file : 99th_DutchClient-0.0.1

set baseversion=%1
set jarfile=%2

if not exist "build" ( 
    mkdir "build"
)
cd "build"

if not exist "extr" ( 
    mkdir "extr"
)
if not exist "fullmodpack" ( 
    mkdir "fullmodpack"
)

echo Extracting vanilla version
cd "extr"
jar -xf %AppData%/.minecraft/versions/%baseversion%/%baseversion%.jar

echo Extracting modded version
cd "../fullmodpack"
jar -xf ../../../build/libs/%jarfile%.jar

echo Copying from vanilla
cd "../"
robocopy "extr" "fullmodpack" "pack.png" /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr" "fullmodpack" "pack.mcmeta" /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr\assets" "fullmodpack\assets" /E /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr\data" "fullmodpack\data" /E /NFL /NDL /NJH /NJS /NC /NS /NP
rmdir /s /q "fullmodpack/META-INF"

echo Preparing modded version
cd "fullmodpack"
jar -cf ../../%jarfile%.jar *

echo Cleaning up
cd ../
rmdir /s /q "extr"
rmdir /s /q "fullmodpack"

pause;
exit;