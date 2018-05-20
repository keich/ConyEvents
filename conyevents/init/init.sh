#!/bin/sh
CONYUSER="conyevents"
CONYPASSWD="conyevents"

. $HOME/bin/env.sh

java org.h2.tools.RunScript -url jdbc:h2:tcp://localhost/~/events -user $CONYUSER -password $CONYPASSWD -script ~/init/init.sql
