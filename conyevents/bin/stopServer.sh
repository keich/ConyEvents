#!/bin/sh 
. $HOME/bin/env.sh
java org.h2.tools.Server -tcpShutdown tcp://localhost
