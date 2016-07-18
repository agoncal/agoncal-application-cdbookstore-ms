#!/usr/bin/env bash

JBOSS_HOME=~/Tools/JBoss/wildfly-10.0.0.Final
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh

$JBOSS_CLI --connect --command="deploy --force cdbookstore-web/target/webCDBookStore.war"
$JBOSS_CLI --connect --command="deploy --force topbooks-ms/target/msTopBooks.war"
$JBOSS_CLI --connect --command="deploy --force topcds-ms/target/msTopCDs.war"
$JBOSS_CLI --connect --command="deploy --force invoices-ms/target/msInvoices.war"
