#*******************************************************************************
# Copyright (c) 2006, 2010 eBay Inc. All Rights Reserved.
# Licensed under the Apache License, Version 2.0 (the "License"); 
# you may not use this file except in compliance with the License. 
# You may obtain a copy of the License at 
#
#    http://www.apache.org/licenses/LICENSE-2.0
#*******************************************************************************
#/bin/sh
echo $WORKSPACE
cd $WORKSPACE
basedir="$WORKSPACE/repository-service-runner/"
directory="$basedir/wso2greg-3.5.1";
wso2filename="wso2greg-3.5.1.zip";
url="http://ph-0147.eva.ebay.com/job/WSO2-Download/lastSuccessfulBuild/artifact/$wso2filename";
#first, i validate an existing wso2 directory, if it's not, go download it
cd $basedir
if [ -d $directory ]; then
	echo "wso2server exists, running wso2server"
else 
	echo "wso2server installation not present. downloading from $url"
	
	wget $url;
        unzip $wso2filename;
fi 


/$directory/bin/wso2server.sh -start;
sleep 20;
cd $basedir;
mvn  clean compile verify;
/$directory/bin/wso2server.sh -stop;
