@Echo off

if not exist "extr" ( 
    mkdir "extr"
)
if not exist "fullmodpack" ( 
    mkdir "fullmodpack"
)

echo Extracting vanilla version
cd "extr"
jar -xf ../%1.jar

echo Extracting modded version
cd "../fullmodpack"
jar -xf ../%2.pre.jar

echo Copying from vanilla
cd "../"
robocopy "extr" "fullmodpack" "pack.png" /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr" "fullmodpack" "pack.mcmeta" /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr\assets" "fullmodpack\assets" /E /NFL /NDL /NJH /NJS /NC /NS /NP
robocopy "extr\data" "fullmodpack\data" /E /NFL /NDL /NJH /NJS /NC /NS /NP
rmdir /s /q "fullmodpack/META-INF"

echo Preparing modded version
cd "fullmodpack"
jar -cf ../%2.jar *

echo Cleaning up
cd ../
rmdir /s /q "extr"
rmdir /s /q "fullmodpack"

exit;