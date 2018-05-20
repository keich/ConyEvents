#!/bin/sh
CONYUSER="conyevents"
CONYPASSWD="conyevents"

. $HOME/bin/env.sh

java org.h2.tools.Shell -url jdbc:h2:tcp://localhost/~/events -user $CONYUSER -password $CONYPASSWD -sql "INSERT INTO EVENTS.STATUS_VIEW (IDENTIFIER,NODE,NODEALIAS,ALERTGROUP,ALERTKEY,SEVERITY,SUMMARY,TYPE) VALUES ( 'TEST123','Test1','localhost','TESTING','SEND',5,'This is test message',2);"
