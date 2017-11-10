#!/bin/bash 
	
echo "MassEngineProcessor is starting..."		

nohup ./MassEngineProcessor-1.0.0.jar -Dlogging.level.com.fl.massengineprocessor=DEBUG &

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "MassEngineProcessor"| awk '{print $2}'`

echo "MassEngineProcessor is started, pid:${MEP_PID}"