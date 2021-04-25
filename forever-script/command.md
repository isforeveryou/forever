linux:
sed -i "s/替换前名字/替换后名字/g" 文件名
'字符串' | tr 'a-z' 'A-Z' ：字符串全转换为大写
'字符串' | tr 'A-Z' 'a-z' ：字符串全转换为小写

ip link set dev 网桥名 down 关闭网桥
ip link del dev 网桥名 down 删除网桥

tcpdump -i any port 端口号 -A -vvvv ：抓包

逐行读取
  1	while read line
	do 
	   echo $line
	done < file.txt
  2     cat file.txt|while read line
	do
	   echo $line
	done

===========================================================================================

mysql(命令行结束要加分号):
mysql -u 用户名 -p 密码 登录mysql

show global variables like 'max_connections'; 查看当前最大连接数
show global variables like 'wait_timeout';查看睡眠时间超时秒数

set global max_connections=?; 设置最大连接数
set global wait_timeout=?;设置睡眠时间超时秒数

show databases;

exit 退出mysql

==========================================================================================

docker :
docker run --name 容器名 -p 宿主机端口:容器端口 -v 宿主机路径:容器路径 -d + 镜像名称：镜像版本 : 启动容器并返回容器id(参数说明:--name 指定容器名,-p 指定端口映射,-v 挂载数据卷,-d 容器后台运行 --network 网桥名 --ip ip地址 )
docker exec -it + containerId bash : 进入容器(推荐)
docker attach + containerId : 进入容器
docker rmi + 镜像id : 删除镜像
docker stop + containerId : 停止容器
docker rm + containerId : 删除容器
docker images : 查看所有镜像
docker ps : 查看所有正在运行的容器
docker ps -q: 只显示容器Id
docker ps -a : 查看所有容器
docker ps --filter name = 容器名 : 根据容器名查看容器
docker port + containerId : 查看映射端口对应的容器内部端口
docker image prune：删除虚悬镜像
docker container prune：删除所有未运行的容器
docker logs -f + containerId : 查看日志
docker network create --subnet=网段/子网长度 网桥名 ：创建网桥
docker network rm 网桥名 : 删除网桥

==========================================================================================

git:
git init：初始化git仓库，生成 .git文件
git remote add 仓库名 远程仓库名：关联两个仓库
git push -u 仓库名 分支名：本地库的分支推到远程仓库（-u 表示推送和关联）
git branch 分支名：新建一个分支
git checkout 分支名：切换到分支
git checkout -b 分支名：新建一个分支并切换到该分支
git remote -v

git commit -am "message" 

git reflog：列出在Git上的操作记录
git reset HEAD@{index} 将代码重置到某个c

===========================================================================================

kubectl：
kubectl get statefulsets -n admin   ##查看创建的服务,正常NAME=服务名
kubectl get configmap -n admin   ##查看configmap，配置文件命名规则：服务名-config,如果是comm的配置文件则为：服务名-agentconfig
statefulset "dpac" deleted    ##删除服务
kubectl get configmap   ##查看configmap，配置文件命名规则：服务名-config,如果是comm的配置文件则为：服务名-agentconfig
kubectl delete configmap dpac-config   ##删除configmap
configmap "dpac-config" deleted
kubectl delete configmap dpac-agentconfig
configmap "dpac-agentconfig" deleted
kubectl exec -it il040uli-0 -c il040uli bash -n admin   进入容器   ilfcre-0
kubectl get pods -n admin  查看服务运行状态   PSSCRA
kubectl get configmap -n admin   ##查看configmap，配置文件命名规则：服务名-config,如果是comm的配置文件则为：服务名-agentconfig
kubectl delete configmap il1wkpu1-config -n admin  ##删除config配置
kubectl delete configmap psscra-agentconfig -n admin  ##agentconfig配置
kubectl exec -it ilflan-0 -c comm-agent  bash -n admin
kubectl exec -it psscra-0 -c psscra  bash -n admin
kubectl delete statefulsets il040ulo -n admin
kubectl delete configmap il040ulo-config -n admin
kubectl delete configmap il040ulo-agentconfig -n admin

==========================================================================================

