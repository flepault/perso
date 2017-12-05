#!/bin/bash 

export DATABASE_SERVER=${SOISRV_DATABASE_SERVER}
export DATABASE_PORT=${SOISRV_DATABASE_PORT}
export ORACLE_SID=${ORACLE_SID}

export LOG_LEVEL=INFO
	
echo "Daily PostGMDProcessor is starting..."		

nohup ./PostGMDProcessor.jar --spring.profiles.active=daily --spring.config.name=postgmdprocessor.daily &

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "PostGMDProcessor"| grep "daily"| awk '{print $2}'`

echo "Daily PostGMDProcessor is started, pid:${MEP_PID}"