#! /bin/bash

num=1
total=$1

if [[ -z $total ]];then
   total=1
   echo "execute parameter is empty,default execute once...."
fi

while ((num <= total))
do
   echo "current-executing number: $num"
   source ./run.sh
   let num++
   sleep 1
done


