# cjs-oauth2-sso-demo
OAuth2 SSO
## 原文：
https://www.cnblogs.com/cjsblog/p/10548022.html

原作者没有实现单点退出功能，再次基础上实现了单点退出：

原理就是每一个sso客户端退出时候，新增Redis保存当前退出的用户userName。
每个系统实现切面aspect注解添加到controller上面，每个请求根据当前系统的userName去校验Redis里面有没有存入相同的userName，存入的话说明当前用户在别的系统退出了，那么清除当前访问项目的局部会话cookies。

将JWT方式的token改成Oauth2系统自己实现的token方式，同时token存入Redis，同时oauth_client_details数据库存储加入Redis缓存。


# demo使用时，新增DNS域名解析（windows）:C:\Windows\System32\drivers\etc\hosts
# 127.0.0.1 myorder.com
# 127.0.0.1 mymember.com
# 127.0.0.1 myauth.com

# 然后进入cmd刷新生效：ipconfig /flushdns


demo使用仅仅需要自己的MySQL数据库，自行修改自己的MySQL地址，执行一下建库建表：\oauth2-sso-auth-server\src\main\resources\permission.sql

demo使用同时需要自己的Redis，自行修改自己的Redis地址

    
    # clients[0]:
      # clientId: OrderManagement
      # clientSecret: order123
    # clients[1]:
      # clientId: UserManagement
      # clientSecret: user123
    # clients[2]:
      # clientId: codeTest
      # clientSecret: order123

# 代码包含自定义登录界面和授权界面	  
	  
	  
# 授权码模式测试（获取授权码--含有授权步骤）
   http://myauth.com:8080/oauth/authorize?response_type=code&client_id=codeTest&redirect_uri=https://www.baidu.com&scope=all
   
   之后根据授权码获取token，然后访问资源  
   
   
# 单点登录，启动认证中心和两个客户端服务member、order
  访问任意一个客户端member：http://mymember.com:8099/memberSystem/member/list
  发现定向到认证中心，登录后又返回member
  同时order也是登录状态。
  
# 单点登出
   一个客户端登出，访问另一个也是登出
   
# 关于自动授权
  oauth_client_details.autoapprove == true(默认Null)