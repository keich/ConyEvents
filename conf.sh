#!/bin/sh
CONYUSER="conyevents"
HOMEDIR="/home"

DISTRDIR=$(dirname "$0")

useradd  -d $HOMEDIR/$CONYUSER -m -U $CONYUSER
cp -R $DISTRDIR/conyevents/* $HOMEDIR/$CONYUSER/
chown -R $CONYUSER:$CONYUSER $HOMEDIR/$CONYUSER

echo "Build Triggers"
su - conyevents -c '/bin/sh -c ~/bin/build.sh'
echo "Run H2 Server"
su - conyevents -c '/bin/sh -c ~/bin/startServer.sh'
sleep 5
echo "Innit database"
su - conyevents -c '/bin/sh -c ~/init/init.sh'
