#!/bin/bash 
	
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "PostGMDProcessor"| grep "daily" | awk '{print $2}'`

echo "Daily PostGMDProcessor is stopping..."		

kill -9 ${MEP_PID}

sleep 5

echo "Daily PostGMDProcessor is stopped"
