#!/bin/bash

export ZOOKEEPER=kafka1.truecaller.net:2181

spark-submit \
        --master local[4] \
        --class "NewUsersDemo" \
        target/scala-2.10/Sparking-assembly-0.1.jar \
        $ZOOKEEPER $1
