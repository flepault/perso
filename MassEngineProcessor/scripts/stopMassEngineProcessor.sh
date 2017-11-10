#!/bin/bash 
	
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "MassEngineProcessor"| awk '{print $2}'`

echo "MassEngineProcessor is stopping..."		

kill -9 ${MEP_PID}

sleep 5

echo "MassEngineProcessor is stopped"
