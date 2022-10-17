#!/bin/csh

if (! ${?CLASSPATH} ) then 
  setenv CLASSPATH "."
else 
  setenv CLASSPATH ".;$CLASSPATH"
endif

echo "==== Running Total Byte Informer ===="
java -Xmx1024m -jar TotalByteInformer.jar
