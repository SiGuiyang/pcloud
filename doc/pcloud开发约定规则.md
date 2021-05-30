## PCloud 接口开发规定规则

### 请求地址
- 接口地址需包含服务名称+业务+操作，如：/admin/user/create `admin 是服务名简写 user 是具体模块 create 是操作模式`
- 新增业务，方法名后缀使用create，使用`POST`请求 如： /xxx/xxx/create
- 更新业务，方法名后缀使用modify，使用`PUT`请求 如： /xxx/xxx/modify
- 分页业务，方法名后缀使用page，使用`POST`请求 如： /xxx/xxx/page
- 列表/数据集业务，方法名后缀使用list，使用`POST`请求 如： /xxx/xxx/list
- 删除业务，方法名后缀使用delete，使用`DELETE`请求 如： /xxx/xxx/delete 请使用`restful`风格
- 单一详情业务，方法名后缀使用info，使用`GET`请求 如：/xxx/xxx/info  请使用`restful`风格
- 复杂获取业务，请使用`POST`请求，必须使用`application/json`风格

### 请求参数与响应

- 接口返回数据响应对象必须是`quick.pager.pcloud.response.ResponseResult<T>`

- 所有请求`request`以及响应`response`的实体类必须实现`Serializable`接口
- 管理后台新增/编辑请求`request`必须继承`quick.pager.pcloud.request.Request`基类
- 管理后台分页请求`request`必须继承`quick.pager.pcloud.request.PageRequest`基类
- `feign`接口返回泛型必须是实现`Serializable`接口的`DTO`模式的实体类
- 渲染前端接口`controller`层返回泛型必须是`Serializable`接口的`DTO`或者`VO`模式的实体类

### 请求风格

- GET / DELETE 请求只能是`restful`风格，请求地址后缀不能携带参数

错误事例：
```
    GET http://127.0.0.1:8080/api/admin/user/info?username=admin
    DELETE http://127.0.0.1:8080/api/admin/user/delete?id=1
```
正确事例：
```
    GET http://127.0.0.1:8080/api/admin/user/{id}/info
    DELETE http://127.0.0.1:8080/api/admin/user/{id}/delete
```

- POST / PUT 请求只能是`application/json`风格，请求地址后缀不能携带参数
错误事例：
```
    POST http://127.0.0.1:8080/api/admin/user/create?username=admin&age=20&avatar=https://wwww.baidu.com/
    PUT http://127.0.0.1:8080/api/admin/user/modify?id=1&username=admin&age=20&avatar=https://wwww.baidu.com/
```
正确事例：
```
    POST http://127.0.0.1:8080/api/admin/user/create
    {
        "username": "admin",
        "age": 20,
        "avatar": "https://wwww.baidu.com/"
    }
    
    PUT http://127.0.0.1:8080/api/admin/user/modify
    {
        "id": 1
        "username": "admin",
        "age": 20,
        "avatar": "https://wwww.baidu.com/"
    }
```
