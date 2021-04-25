#! /bin/bash

ip=172.21.67.105

function send()
{
   if [[ -z $1 ]] || [[ $1 == success ]];then
      echo 'url is empty or completed,skip this node.'
      return 0
   fi
  
   result=$(curl $ip:$1)
   if [[ ${result} != *COMPLETED ]];then
      echo 'The node execute failed ---> '$ip:$1 : ${result}
      return 1
   fi 

   echo 'The node execute success ---> '$ip:$1 : ${result}
   return 2
}


path=$(pwd)
sourcefile=$path/url.txt
breakpoint=$path/breakpoint.txt

while getopts :af: opt
do
  case $opt in
      a)
        echo "option:a ---> execute all node"
        sudo rm -rf breakpoint.txt;;
      f)
        echo "option:f ---> execute point file:$OPTARG"
        sourcefile=$path/$OPTARG
        sudo rm -rf breakpoint.txt;;
      ?)
        echo "unknow option ---> execute default option";;
  esac
done


if [ ! -f "$sourcefile" ];then
   echo 'source file inexistence,shell done.....'
   exit
fi

if [ ! -f "$breakpoint" ];then
   cp $sourcefile ./breakpoint.txt
fi


cat breakpoint.txt | sed s/[[:space:]]//g | while read line
do
   send $line
   num=$?
   if [[ ${num} == 2 ]];then
      sed -i "s!$line!success!g" breakpoint.txt
   elif [[ ${num} == 1 ]];then
      exit 1
   fi
done

n=$?
if [[ ${n} == 1 ]];then
   exit 1
fi
echo 'completion of task....'
sudo rm -rf breakpoint.txt



