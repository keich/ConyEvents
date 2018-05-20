#!/bin/sh
CONYUSER="conyevents"
CONYPASSWD="conyevents"

. $HOME/bin/env.sh

java org.h2.tools.Shell -url jdbc:h2:tcp://localhost/~/events -user $CONYUSER -password $CONYPASSWD -sql "UPDATE EVENTS.STATUS_VIEW SET SUMMARY='This is test message2' WHERE IDENTIFIER='TEST123';"
