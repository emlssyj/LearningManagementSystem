# 设计文档

#### 登录模块（已经过MVVM重构）

输入账号密码后点击submit按钮登录，登录成功后提示欢迎标语并跳转至主界面，如果登录失败则提示账号密码错误标语。



![image-20200104120704872](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120704872.png)

#### ![](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120742350.png)



#### 主界面

在主页面中通过recyclerview展示服务器上所有的课程信息，并可以通过搜索栏自定义filter进行模糊过滤，在单击课程名后会跳转到课程详细信息页面。下方有导航栏，可以通过点击“浏览”选项切换到浏览界面。

![](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120802028.png)



![image-20200104120828989](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120828989.png)

#### 课程详细信息界面

在课程详细信息界面中，可以看到从服务器上加载的课程的所有信息，包括课程名、教师照片、教师姓名、课程介绍以及课程多媒体资料等。点击多媒体资料部分的“播放”图标，可以跳转到多媒体播放界面。

![image-20200104120917417](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120917417.png)

#### 多媒体播放界面

在此界面中，可以通过进图条来拖动控制播放进度，也可进行暂停、播放、快进快退等操作，但由于服务器不是以流的形式传输文件，所以视频在播放的时候会出现严重卡顿

![image-20200104120936074](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104120936074.png)

#### 浏览页面

通过使用基于recyclerview的第三方接口twowayview来实现浏览界面，接收来自服务器的课程信息并展现在twowayview中。

https://github.com/lucasr/twoway-view

在此界面点击播放按钮同样会跳转至多媒体播放界面

![image-20200104121904803](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20200104121904803.png)