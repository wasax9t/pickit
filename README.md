# pickit
后台监听剪贴板是图片，上传到七牛云，往剪贴板写图片外链
## 运行
1. 需要项目根目录下新建`qiniu.ini`文件，文件内容：  
* 第一行	AccessKey  
* 第二行	SecretKey  
* 第三行 bucket_name
* 第四行 外链域名（未实现，写着觉得应该有）
2. 修改main.java中的外链域名QINIU_URL, 及图片储存位置TMP_PATH，可以直接运行main试一下
3. 执行`mvn assembly:single`
4. 将打包出的jar与这几个东西放一起如图  
![最后需要的文件](http://or4o97iuj.bkt.clouddn.com/prtimg20170818_175125.png)  
5. 运行`start.vbs`即可，注意：需要JDK1.8以上
## 另
如果有闲心或被需求，可以实现：
1. UI添加设置以上四项还有图片本地保存位置功能
2. 系统托盘弹出菜单增加功能暂停
3. 主窗口把图片拖进来上传
