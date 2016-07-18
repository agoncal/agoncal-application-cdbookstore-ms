#!/usr/bin/env bash

JBOSS_HOME=~/Tools/JBoss/wildfly-10.0.0.Final
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh

$JBOSS_CLI --connect --command="undeploy webCDBookStore.war"
$JBOSS_CLI --connect --command="undeploy msTopBooks.war"
$JBOSS_CLI --connect --command="undeploy msTopCDs.war"
$JBOSS_CLI --connect --command="undeploy msInvoices.war"
