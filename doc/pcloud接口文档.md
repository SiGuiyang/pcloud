## PCloud接口文档

[TOC]

## 管理后台

一、基础服务

 1. 登录

    - 接口地址 	`/api/oauth/token`

    - 请求模式     `POST`

    - 负责人        `pcloud`

      请求参数

      ```json
      {
        "phone": "",		// 手机号
        "secureId":"",		// secureId
      	"secureKey":"",		// secureKey
        "password":"",		// 登录密码
        "grantType":"",		// 授权方式
        "verifyCode":""		// 短信验证码
      }
      ```

      响应结果

      ```json
      {
      	"code": 200,
        "msg": "success",
        "timestamp":1444440000
        "data": {
        	
      	}
      }
      ```

 2. 注销

二、系统管理

1. 用户管理

   1. 用户列表

      - 接口地址 	`/api/admin/system/user/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数`分页`

        ```json
        {
          "page": 1,		// 页码
          "pageSize": 10,		// 页数
        	"phone":"",		// 手机号码
          "roleIds":[1],		// 角色主键集
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": {
        		"avatar": "https://pcloud-1301231397.cos.ap-nanjing.myqcloud.com/static/huolongguo.jpg",	// 头像
        		"gmtModifiedDate": "2021-02-15T18:49:43",	// 更新时间
        		"gmtModifiedName": "admin",		// 操作人
        		"id": 19,											// 主键
        		"name": "测试",								// 姓名
        		"online": true,							 //	在线标识
        		"password": "$2a$10$3NwjBzYkVU5bgQececGfBurtkqKd.6RbgVSgqzMI6vsQ3zPWNvOWm",		// 密码
        		"phone": "13818471388",		// 登录账号
        		"roles": [],							// 角色
        		"state": false						// 状态
        	}
        }
        ```

   2. 用户新增

      - 接口地址 	`/api/admin/system/user/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
            "phone":"",  // 手机号码
           	"name":"", 	// 昵称
            "password": "", 	// 密码
            "state": 0, 		// 0 ：正常 1：禁用
        		"roleIds": [1,2],		// 角色
        		"avatar": ""			// 头像
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1		// 新增主键
        }
        ```

   3. 用户编辑

      - 接口地址 	`/api/admin/system/user/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "id":	1,		// 主键
          "phone":"",  // 手机号码
          "name":"", 	// 昵称
          "password": "", 	// 密码
          "state": 0, 		// 0 ：正常 1：禁用
        	"roleIds": [1,2],		// 角色
        	"avatar": ""			// 头像
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1		// 编辑主键
        }
        ```

   4. 强踢下线

      - 接口地址 	`/api/admin/system/user/{id}/offline`

      - 请求模式     `GET`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

   5. 用户导出

      - 接口地址 	`/api/admin/system/user/download`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "ids":[1,3,4,5],		// 用户主键集
          "fileName": ""		// 导出文件名称
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

2. 角色管理

   1. 角色列表

      - 接口地址 	`/api/admin/role/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        	"page": 1,		// 页码
          "pageSize": 10,		// 页数
        	"name":"",		// 角色名称
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": {
          	"gmtModifiedDate": "2021-03-20T09:44:18",		// 更新时间
        		"gmtModifiedName": "admin",		// 操作人
        		"id": 2,		// 主键
        		"name": "普通管理员",		// 角色名称
        		"roleCode": "ROLE_ADMIN"	// 角色编码
        	}
        }
        ```

      

   2. 角色刷新

      - 接口地址 	`/api/admin/role/refresh`

      - 请求模式     `GET`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

   3. 角色新增

      - 接口地址 	`/api/admin/role/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "name":"", 	// 角色名称
          "roleCode":""		// 角色编码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1		// 新增主键
        }
        ```

      

   4. 角色编辑

      - 接口地址 	`/api/admin/role/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "id":1,		// 主键
          "name":"", 	// 角色名称
          "roleCode":""		// 角色编码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1		// 编辑主键
        }
        ```

      

   5. 角色删除

      - 接口地址 	`/api/admin/role/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
        }
        ```

      

   6. 授权列表

      - 接口地址 	`/api/admin/role/{roleId}/menu`

      - 请求模式     `GET`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": {
            "hasRouters": [1,2,3,4],		// 路由拥有的权限主键
            "routers": [								// 路由
              {
        				"id": 1,								// 主键
                "parentId": 0,					// 父级主键
                "name": "",							// 名称
                "label":"",							// 名称
                "children": [						// 子节点
                  {
                    "id": 1,
                		"parentId": 0,
                		"name": "",
               		 	"label":"",
                  }
                ]
              }
            ],							
        		"hasResources": [5,6,7,8],	// 拥有的资源权限
            "resources": [							// 资源权限
            	{
                "id": 1,								// 主键
                "parentId": 0,					// 父级主键
                "name": "",							// 名称
                "label":"",							// 名称
                "children": [						// 子节点
                  {
                    "id": 1,
                		"parentId": 0,
                		"name": "",
               		 	"label":"",
                  }
                ]
              }
            ]
        	}
        }
        ```

      

   7. 角色授权

      - 接口地址 	`/api/admin/permission/grant`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "routers": [1,2,3,4],			// 权限
          "resources":[5,6,7,8],		// 访问资源
          "roleId": 1								// 角色主键	
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

3. 部门管理

   1. 部门列表

      - 接口地址 	`/api/admin/dept/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        	"page": 1,		// 页码
          "pageSize": 10,		// 页数
        	"name":"",		// 角色名称
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": [
            {
        			"gmtModifiedDate": "2021-01-17T15:46:28",		// 更新时间
        			"gmtModifiedName": "admin",									// 操作人
        			"id": 15,																		// 主键
        			"label": "业务中台部",												 // 部门名称
        			"managerId": null,													// 部门经理主键 
        			"managerName": null,												// 部门经理名称
        			"name": "业务中台部",												// 部门名称
        			"parentId": 3,															// 父级主键 
        			"parentName": "技术部"												// 上级部门
            }
          ]
        }
        ```

      

   2. 部门新增

      - 接口地址 	`/api/admin/dept/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "parentId": 0,		// 上级部门主键
          "name":"",			 // 部门名称
          "managerId": 1		// 部门经理主键
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1	// 新增主键
        }
        ```

      

   3. 部门编辑

      - 接口地址 	`/api/admin/dept/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "id": 1,					// 主键
          "parentId": 0,		// 上级部门主键
          "name":"",			 // 部门名称
          "managerId": 1		// 部门经理主键
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": 1	// 编辑主键
        }
        ```

      

   4. 部门删除

      - 接口地址 	`/api/admin/dept/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

   5. 部门视图

      - 接口地址 	`/api/admin/dept/org`

      - 请求模式     `GET`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000,
          "data": {
          	"id":1,		// 部门主键
            "parentId": 0, // 上级部门主键
            "parentName": "", // 上级部门
            "name": "",		// 部门名称
            "label":"", 		// 部门名称
            "children": [	// 子节点
            	{
                "id":1,		// 部门主键
            		"parentId": 0, // 上级部门主键
            		"parentName": "", // 上级部门
            		"name": "",		// 部门名称
           		 	"label":"", 		// 部门名称
              }
            ]
        	}
        }
        ```

      

4. 岗位管理

   1. 岗位列表

      - 接口地址 	`/api/admin/post/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 岗位新增

      - 接口地址 	`/api/admin/post/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 岗位编辑

      - 接口地址 	`/api/admin/post/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 岗位删除

      - 接口地址 	`/api/admin/post/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

5. 职级管理

   1. 职级列表

      - 接口地址 	`/api/admin/grade/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 职级新增

      - 接口地址 	`/api/admin/grade/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 职级编辑

      - 接口地址 	`/api/admin/grade/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 职级删除

      - 接口地址 	`/api/admin/grade/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

6. 菜单管理

   1. 菜单列表

      - 接口地址 	`/api/admin/menu/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 菜单新增

      - 接口地址 	`/api/admin/menu/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 菜单编辑

      - 接口地址 	`/api/admin/menu/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 菜单删除

      - 接口地址 	`/api/admin/menu/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

7. 访问资源

   1. 资源列表

      - 接口地址 	`/api/admin/resource/page`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 资源新增

      - 接口地址 	`/api/admin/resource/create`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 资源编辑

      - 接口地址 	`/api/admin/resource/modify`

      - 请求模式     `PUT`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 资源删除

      - 接口地址 	`/api/admin/resource/{id}/delete`

      - 请求模式     `DELETE`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
        
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
        }
        ```

      

8. 操作日志

   - 接口地址 	`/api/admin/log/action/page`

   - 请求模式     `POST`

   - 负责人        `pcloud`

     请求参数

     ```json
     {
       "phone": "",		// 手机号
       "secureId":"",		// secureId
     	"secureKey":"",		// secureKey
       "password":"",		// 登录密码
       "grantType":"",		// 授权方式
       "verifyCode":""		// 短信验证码
     }
     ```

     响应结果

     ```json
     {
     	"code": 200,
       "msg": "success",
       "timestamp":1444440000
       "data": {
       	
     	}
     }
     ```

三、个性化服务



四、开放平台



五、号段管理

 1. 号段列表

    - 接口地址 	`/api/integration/admin/segment/page`

    - 请求模式     `POST`

    - 负责人        `pcloud`

      请求参数

      ```json
      {
        "phone": "",		// 手机号
        "secureId":"",		// secureId
      	"secureKey":"",		// secureKey
        "password":"",		// 登录密码
        "grantType":"",		// 授权方式
        "verifyCode":""		// 短信验证码
      }
      ```

      响应结果

      ```json
      {
      	"code": 200,
        "msg": "success",
        "timestamp":1444440000
        "data": {
        	
      	}
      }
      ```

    

 2. 号段新增

    - 接口地址 	`/api/integration/admin/segment/create`

    - 请求模式     `POST`

    - 负责人        `pcloud`

      请求参数

      ```json
      {
        "phone": "",		// 手机号
        "secureId":"",		// secureId
      	"secureKey":"",		// secureKey
        "password":"",		// 登录密码
        "grantType":"",		// 授权方式
        "verifyCode":""		// 短信验证码
      }
      ```

      响应结果

      ```json
      {
      	"code": 200,
        "msg": "success",
        "timestamp":1444440000
        "data": {
        	
      	}
      }
      ```

    

 3. 号段编辑

    - 接口地址 	`/api/integration/admin/segment/modify`

    - 请求模式     `PUT`

    - 负责人        `pcloud`

      请求参数

      ```json
      {
        "phone": "",		// 手机号
        "secureId":"",		// secureId
      	"secureKey":"",		// secureKey
        "password":"",		// 登录密码
        "grantType":"",		// 授权方式
        "verifyCode":""		// 短信验证码
      }
      ```

      响应结果

      ```json
      {
      	"code": 200,
        "msg": "success",
        "timestamp":1444440000
        "data": {
        	
      	}
      }
      ```

六、定时任务

1. 运行报表

   1. 任务统计

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 任务视图报表统计

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

2. 任务管理

   1. 任务列表

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 任务组列表

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 任务新增

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 任务编辑

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   5. 任务删除

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   6. 任务停止

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   7. 任务复制

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   8. 任务执行一次

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   9. 任务节点

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

   10. 任务下次执行时间

       - 接口地址 	`/api/oauth/token`

       - 请求模式     `POST`

       - 负责人        `pcloud`

         请求参数

         ```json
         {
           "phone": "",		// 手机号
           "secureId":"",		// secureId
         	"secureKey":"",		// secureKey
           "password":"",		// 登录密码
           "grantType":"",		// 授权方式
           "verifyCode":""		// 短信验证码
         }
         ```

         响应结果

         ```json
         {
         	"code": 200,
           "msg": "success",
           "timestamp":1444440000
           "data": {
           	
         	}
         }
         ```

       

3. 调度日志

   1. 日志列表

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

   2. 日志清理

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

4. 执行器管理

   1. 执行器列表

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   2. 执行器新增

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   3. 执行器编辑

      - 接口地址 	`/api/oauth/token`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

      

   4. 执行器删除

      - 接口地址 	`/api/job/jobgroup/pageList`

      - 请求模式     `POST`

      - 负责人        `pcloud`

        请求参数

        ```json
        {
          "phone": "",		// 手机号
          "secureId":"",		// secureId
        	"secureKey":"",		// secureKey
          "password":"",		// 登录密码
          "grantType":"",		// 授权方式
          "verifyCode":""		// 短信验证码
        }
        ```

        响应结果

        ```json
        {
        	"code": 200,
          "msg": "success",
          "timestamp":1444440000
          "data": {
          	
        	}
        }
        ```

七、外部链接



## 移动端

