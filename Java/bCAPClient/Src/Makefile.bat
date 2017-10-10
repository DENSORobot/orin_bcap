echo off

rem =====▽ ビルドする環境に合わせて修正してください．▽
set JAVAROOT=C:\Program Files\Java\jdk1.7.0_21
set ORINROOT=C:\ORiN2
rem ===== △ ここまで．△

cd /d "%ORINROOT%\CAP\b-CAP\CapLib\DENSO\RC8\Include\Java\bCAPClient\Src"

rem =====Build=====
"%JAVAROOT%\Bin\javac.exe" -d ..\Bin orin2\library\ConnOptParser.java orin2\library\HResult.java orin2\library\ORiN2Exception.java orin2\bcap\BCAPByteConverter.java orin2\bcap\BCAPClient.java orin2\bcap\BCAPConnectionBase.java orin2\bcap\BCAPConnectionTCP.java orin2\bcap\BCAPConnectionUDP.java orin2\bcap\BCAPDefine.java orin2\bcap\BCAPPacket.java orin2\bcap\BCAPPacketConverter.java
rem ===============

cd /d "%ORINROOT%\CAP\b-CAP\CapLib\DENSO\RC8\Include\Java\bCAPClient\Bin"

rem =====Make jar file=====
"%JAVAROOT%\Bin\jar.exe" cvf jbCAPClient.jar .\
rem =======================