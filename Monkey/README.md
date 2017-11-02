Para compilar ejecutar el siguiente comando:

javac -cp commons-cli-1.4.jar;commons-net-3.6.jar Main.java

Para ejecutar se pueden usar los siguientes comandos de ejemplo:

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main (To get usage help on the commands)

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Keyevent 10 -Swipe 10 -Tap 10 -Text 70

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Keyevent 100

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Swipe 100

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Tap 100

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Text 100

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Tap 100

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Tap 100 -APKSourcePath "E:\\03 Talleres\\07 Taller 7\\Monkey\\" -APKName org.tasks_481.apk -APKPackageName org.tasks

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Tap 100 -APKSourcePath "E:\\03 Talleres\\07 Taller 7\\Monkey\\" -APKName org.tasks_481.apk -APKPackageName org.tasks

java -cp commons-cli-1.4.jar;commons-net-3.6.jar;. Main -Events 40 -Keyevent 10 -Swipe 10 -Tap 10 -Text 10 -Rotate 20 -Sensor 20 -Network 20 -APKSourcePath "E:\\03 Talleres\\07 Taller 7\\Monkey\\" -APKName org.tasks_481.apk -APKPackageName org.tasks

Descripcion:

Se utilizaron dos librerias de Apache para poder manejar los parametros recibidos por la linea de comandos y para poder ejecutar el Telnet en Windows.

El parametro Events define el numero de eventos a ejecutar por el Monkey.

Se usan los siguientes comandos:

Tap
Text
Swipe
Keyevent
Rotate
Network
Sensor

Al escribir cada uno de los comandos se debe escribir el correspondiente numero que define la distribucion de la ejecucion de dicho comando con respecto al total de eventos definidos que se deben ejecutar. En base al numero de eventos a ejecutar y a esta distribucion se calcula el numero de ejecuciones de cada uno de los comandos.

Para la parte del APK se usaron los siguientes parametros:

APKSourcePath, APKName, y APKPackageName

Ejemplos:

APKSourcePath    : "E:\\03 Talleres\\07 Taller 7\\Monkey\\"  Se deben incluir las dobles comillas y separar con doble \
APKName          : org.tasks_481.apk
APKPackageName   : org.tasks

El Monkey instala el APK y despues comienza la ejecucion de los comandos.
