#! /bin/bash

day=1
username=100105
password=123456

while getopts :u:p:d: opt
do
  case $opt in
      d)
	day=$OPTARG;;
      u)
        username=$OPTARG;;
      p)
        password=$OPTARG;;
      ?)
        echo "unknow option ---> execute default option";;
  esac
done

param={\"emplyCd\":\"$username\",\"pwd\":\"$password\"}
sid=$(echo $(curl -H "Content-Type:application/json" -X POST -d $param 172.21.67.105:9000/v01/emplycenter/login) | grep -Po '(?<="data":")[^"]*')

if [[ -z $sid ]];then
   echo "emply center login failed,plase check request param"
   exit 1
fi
echo "emply center login success......"

param={\"appProps\":{\"srcBizSeqNo\":\"999988888\",\"srcBizDate\":\"\",\"userLang\":\"\",\"txOrgNo\":\"1800\",\"txLprOrgNo\":\"\",\"txUserId\":\"$username\",\"retStatus\":\"\",\"trgBizDate\":\"\",\"trgBizSeqNo\":\"\",\"orgChannelType\":\"wc\",\"globalBizSeqNo\":\"123\",\"orgPartnerId\":\"\",\"orgScenarioId\":\"\",\"orgSysId\":\"iloan\",\"reverseSeqNo\":\"\",\"srcDcn\":\"\",\"srcServerId\":\"\",\"srcServiceId\":\"\",\"srcTopicId\":\"\",\"srcSysVersion\":\"\",\"srcTimeStamp\":\"\",\"trgTopicId\":\"\",\"trgServiceId\":\"\",\"trgTimeStamp\":\"\",\"txAuthFlag\":\"\",\"txDeptCode\":\"\",\"funcUrl\":\"/login\"},\"body\":\"{\\\"form\\\":[{\\\"FormData\\\":{\\\"sidId\\\":\\\"$sid\\\"},\\\"FormHead\\\":{\\\"FormId\\\":{}}}]}\",\"clientTyp\":\"01\",\"bizAccessChnlCd\":\"0101\",\"reqePtyProdCd\":\"500\",\"bizFolnNo\":\"\",\"sysFolnNo\":\"\",\"sceneCd\":\"iloan\",\"servPtyProdCd\":\"\",\"servCd\":\"\",\"reqePtyReqeTs\":\"\",\"reqePtyIp\":\"\",\"linksFolnNo\":\"\",\"reqePtyAppId\":\"iloan-web\",\"reqePtyZoneNm\":\"\",\"custNo\":\"\"}
lgn=$(echo $(curl -H "Content-Type:application/json" -X POST -d $param 172.21.67.105:9000/v01/sm/system-manage/user/login) | grep -Po '(?<="lgnVouchCd":")[^"]*')

if [[ -z $lgn ]];then
   echo "iloan login failed,please check request param"
   exit 1
fi
echo "iloan login success......."


onlineDate=$(echo $(curl -H "Content-Type:application/json" -H "lgnVouchCd:$lgn" -H "funcUrl: /daily_server" -X POST  -d '{"appProps":{"srcBizSeqNo":"999988888","srcBizDate":"","userLang":"","txOrgNo":"9909","txLprOrgNo":"","txUserId":"100105","retStatus":"","trgBizDate":"","trgBizSeqNo":"","orgChannelType":"wc","globalBizSeqNo":"123","orgPartnerId":"","orgScenarioId":"","orgSysId":"iloan","reverseSeqNo":"","srcDcn":"","srcServerId":"","srcServiceId":"","srcTopicId":"","srcSysVersion":"","srcTimeStamp":"","trgTopicId":"","trgServiceId":"","trgTimeStamp":"","txAuthFlag":"","txDeptCode":"","funcUrl":"/daily_server"},"body":"{\"form\":[{\"FormData\":{},\"FormHead\":{\"FormId\":\"SM1SML01O001\"}}]}","clientTyp":"01","bizAccessChnlCd":"0101","reqePtyProdCd":"500","bizFolnNo":"","sysFolnNo":"","sceneCd":"iloan","servPtyProdCd":"","servCd":"","reqePtyReqeTs":"","reqePtyIp":"","linksFolnNo":"","reqePtyAppId":"iloan-web","reqePtyZoneNm":"","custNo":""}' 172.21.67.105:9000/v01/sm/system-manage/dateswitch/query) | grep -Po '(?<="onlineBizDt":")[^"]*')

if [[ -z $onlineDate ]];then
   echo "getting onlineDate failed,please check request param"
   exit 1
fi


if echo $day |grep -Eq "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}" && date -d $day +%Y%m%d >/dev/null 2>&1
then
   day=$((($(date +%s -d $day) - $(date +%s -d $onlineDate))/86400))
fi

echo "current date:$onlineDate,plan run days:$day"

num=1
records=""
while ((num <= day))
do
   inDate=$(date -d "$onlineDate +$num day" +%Y-%m-%d)
   echo "The $num date:$inDate"
   let num++
   records="$records{\\\"bizDt\\\":\\\"$inDate\\\"},"
done

param={\"appProps\":{\"srcBizSeqNo\":\"999988888\",\"srcBizDate\":\"\",\"userLang\":\"\",\"txOrgNo\":\"9909\",\"txLprOrgNo\":\"\",\"txUserId\":\"$username\",\"retStatus\":\"\",\"trgBizDate\":\"\",\"trgBizSeqNo\":\"\",\"orgChannelType\":\"wc\",\"globalBizSeqNo\":\"123\",\"orgPartnerId\":\"\",\"orgScenarioId\":\"\",\"orgSysId\":\"iloan\",\"reverseSeqNo\":\"\",\"srcDcn\":\"\",\"srcServerId\":\"\",\"srcServiceId\":\"\",\"srcTopicId\":\"\",\"srcSysVersion\":\"\",\"srcTimeStamp\":\"\",\"trgTopicId\":\"\",\"trgServiceId\":\"\",\"trgTimeStamp\":\"\",\"txAuthFlag\":\"\",\"txDeptCode\":\"\",\"funcUrl\":\"/plan_add\"},\"body\":\"{\\\"form\\\":[{\\\"FormData\\\":{\\\"records\\\":[${records%?}]},\\\"FormHead\\\":{\\\"FormId\\\":\\\"SM1SML01O001\\\"}}]}\",\"clientTyp\":\"01\",\"bizAccessChnlCd\":\"0101\",\"reqePtyProdCd\":\"500\",\"bizFolnNo\":\"\",\"sysFolnNo\":\"\",\"sceneCd\":\"iloan\",\"servPtyProdCd\":\"\",\"servCd\":\"\",\"reqePtyReqeTs\":\"\",\"reqePtyIp\":\"\",\"linksFolnNo\":\"\",\"reqePtyAppId\":\"iloan-web\",\"reqePtyZoneNm\":\"\",\"custNo\":\"\"}

status=$(echo $(curl -H "Content-Type:application/json" -H "lgnVouchCd:$lgn" -H "funcUrl: /plan_add" -X POST  -d $param 172.21.67.105:9000/v01/sm/system-manage/dateswitch/plan-add) | grep -Po '(?<="retStatus":")[^"]*')

if [[ $status == F ]];then
   echo "insert plan failed,please check request param"
   exit 1
fi

echo "insert plan success,prepare execute batch task......"




