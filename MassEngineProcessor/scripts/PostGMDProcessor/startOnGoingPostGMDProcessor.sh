#!/bin/bash 

export DATABASE_SERVER=${SOISRV_DATABASE_SERVER}
export DATABASE_PORT=${SOISRV_DATABASE_PORT}
export SID=${ORACLE_SID}
	
echo "OnGoing PostGMDProcessor is starting..."		

nohup ./PostGMDProcessor.jar --spring.profiles.active=ongoing --spring.config.name=postgmdprocessor.daily&

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "PostGMDProcessor"| grep "ongoing"| awk '{print $2}'`

echo "OnGoing PostGMDProcessor is started, pid:${MEP_PID}"