## php

php 端口配置
``` bash
/etc/apache2/sites-available/000-default.conf
```
php 重启
``` bash
/etc/init.d/apache2 restart
```
测试php 配置是否正确
``` bash
apache2ctl configtest
```
php www文件位置
``` bash
/var/www/html
```
php 端口 8085


## nginx
``` bash
/home/nginx/webserver/nginx
```

``` bash
nginx -s reload
```

nginx.conf example
```
http {
	server {
		listen 80;
		server_name 192.168.10.1;
		charset utf-8;
		location /leian {
			alias "/app/www/leian";
			index index.html;
		}
	}
}
```



## find
``` bash
find / -name 'jenkins.xml'
```

## jenkins
### config
``` bash
vi /etc/default/jenkins
```
重启 jenkins
``` bash
/etc/init.d/jenkins restart
```
/etc/init.d/jenkins 使用说明
``` bash
Usage: /etc/init.d/jenkins {start|stop|status|restart|force-reload}
workspace /var/lib/jenkins/workspace
```

## others
``` bash
ps -aux --sort=+%mem
free -m
```
Permission denied
``` bash
chmod u+x example.sh
```


# Windows
 > Jenkins 启动/关闭

 - 用管理员身份启动cmd，否则执行命令报错：否则会报系统错误5
 - 进入jenkins安装根目录
``` bash
net start jenkins
net stop jenkins
```
