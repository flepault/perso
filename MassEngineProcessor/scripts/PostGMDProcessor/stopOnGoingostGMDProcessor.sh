#!/bin/bash 
	
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "PostGMDProcessor"| grep "ongoing" | awk '{print $2}'`

echo "OnGoing PostGMDProcessor is stopping..."		

kill -9 ${MEP_PID}

sleep 5

echo "OnGoing PostGMDProcessor is stopped"
