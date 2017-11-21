#!/bin/bash 
	
echo "Daily PostGMDProcessor is starting..."		

nohup ./PostGMDProcessor.jar --spring.profiles.active=daily --spring.config.name=postgmdprocessor.daily&

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "PostGMDProcessor"| grep "daily"| awk '{print $2}'`

echo "Daily PostGMDProcessor is started, pid:${MEP_PID}"