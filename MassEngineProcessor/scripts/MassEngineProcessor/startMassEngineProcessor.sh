#!/bin/bash 
	
echo "MassEngineProcessor is starting..."		

nohup ./MassEngineProcessor.jar --spring.config.name=massengineprocessor&

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "MassEngineProcessor"| awk '{print $2}'`

echo "MassEngineProcessor is started, pid:${MEP_PID}"