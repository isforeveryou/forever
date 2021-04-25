#! /bin/bash

#代码分支
codebranch=v20201112
#载入其他sh文件
source /opt/lego-iloan/kit-package/config
KIT_PACK=/opt/lego-iloan/kit-package

#filename=$(pwd)
filename=$(cd "$(dirname $0)";pwd)
#path=$(pwd)
path=$(cd "$(dirname $0)";pwd)
cd $path
filename="${filename##*/}"

#大写转小写
filename=${filename}* | tr 'A-Z' 'a-z'

echo -e '*** filename is:'  ${filename}
echo -e '*** path is:' ${path}
echo -e '\nStep1/5 cloning code from git@'${GIT}:${LIB}/${filename}'.git .'

#删除旧代码
sudo rm -rf ${filename}
#git上拉新代码
if [ ! $codebranch]; then
  sudo git clone git@${GIT}:${LIB}/${filename}.git
else
  sudo git clone -b ${codebranch} git@${GIT}:${LIB}/${filename}.git
fi


echo -e '\nStep3/5 copy file supervisord.conf Dockerfile settings.xml .'
sudo rm -rf ./supervisord.conf 
sudo rm -rf ./Dockerfile
sudo cp ${KIT_PACK}/supervisord.conf ./
sudo cp ${KIT_PACK}/Dockerfile ./
sudo cp ${KIT_PACK}/settings.xml ./${filename}/
#替换当前代码路径
sed -i "s/{APP_NAME}/${filename}/g" Dockerfile
#替换运行jar包名称
sed -i "s/{APP_NAME}/${filename}/g" supervisord.conf

#当前运行容器id
echo -e '\nStep4/5 building docker image.'
container=$(sudo docker ps -a -q --filter ancestor=${filename}:latest)

#构建新镜像
msg=$(sudo docker build -t ${filename}:latest .)
echo -e $msg
if [[ ! ${msg} =~ "Successfully built " ]];then
	echo 'error: building docker image fail...process end.'
	exit
fi

#停止并删除旧容器
echo -e '\nStep5/5 remove container and run new one.'  
if [ ${container} ];
then
  echo 'remove container: '${container}
  sudo docker rm $(sudo docker stop ${container})
fi

#运行新容器(host方式运行)
echo 'run new container'
container=$(sudo docker run --name ${filename} --net=host -v ${path%/*}/logs/${filename}/:/home/admin/logs/ -d ${filename}:latest)
echo ${container}

echo 'success: all done!'

#sleep 6
#log=$(ls *.log)
#if [ ${log} ];
#then
#  tail -f ${log}
#else
#  sudo docker logs -f ${container}
#fi
#echo 'isclm' | sudo -S docker logs -f ${container}



