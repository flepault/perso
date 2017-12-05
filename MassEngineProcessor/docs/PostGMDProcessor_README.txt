################
# INSTALLATION #
################

1- Java version should be 1.6
2- Following environment variable should be set : 
	- SOISRV_DATABASE_SERVER
	- SOISRV_DATABASE_PORT
	- ORACLE_SID
3- Deploy & Unzip PostGMDProcessor-1.0.0.zip
4- To run PostGMDProcessor for on going treatment use : startOnGoingPostGMDProcessor.sh
5- To run PostGMDProcessor for daily treatment use : startDailyPostGMDProcessor.sh

#############
# LOG LEVEL # 
#############

To increase log level from INFO to DEBUG, you can update LOG_LEVEL value in following files:
- startOnGoingPostGMDProcessor.sh
- startDailyPostGMDProcessor.sh




