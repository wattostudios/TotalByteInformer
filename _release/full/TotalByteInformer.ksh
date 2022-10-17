#!/bin/ksh
export CLASSPATH=".;$CLASSPATH"
echo "==== Running Total Byte Informer ===="
java -Xmx1024m -jar TotalByteInformer.jar
