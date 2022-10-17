echo off
SET CLASSPATH=.;%CLASSPATH%
cls

echo "==== Running Total Byte Informer ===="
java -Xmx1024m -jar TotalByteInformer.jar