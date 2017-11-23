#!/bin/bash 

export DATABASE_SERVER=${SOISRV_DATABASE_SERVER}
export DATABASE_PORT=${SOISRV_DATABASE_PORT}
export ORACLE_SID=${ORACLE_SID}
	
echo "MassEngineProcessor is starting..."		

nohup ./MassEngineProcessor.jar --spring.config.name=massengineprocessor&

sleep 5
MEP_PID=`ps -edf | grep $LOGNAME | grep "java" | grep "MassEngineProcessor"| awk '{print $2}'`

echo "MassEngineProcessor is started, pid:${MEP_PID}"