## PCloud数据库设计文档

### 管理后台中心设计

`登录账户表 t_sys_user`

| 名       | 类型          | 必填 | 注释               |
| -------- | ------------- | ---- | ------------------ |
| id       | bigint(20)    | YES  | 主键               |
| phone    | varchar(63)   | YES  | 登录账户           |
| name     | varchar(63)   | YES  | 昵称               |
| password | varchar(63)   | YES  | 登录密码           |
| state    | bit(1)        | YES  | '0 ：正常 1：禁用' |
| avatar   | varchar(1000) | NO   | 头像               |

 `角色表t_role`

| 名        | 类型        | 必填 | 注释     |
| --------- | ----------- | ---- | -------- |
| id        | bigint(20)  | YES  | 主键     |
| name      | varchar(63) | YES  | 角色名称 |
| role_code | varchar(63) | YES  | 角色编码 |

`菜单表t_menu`

| 名        | 类型         | 必填 | 注释                        |
| --------- | ------------ | ---- | --------------------------- |
| id        | bigint(20)   | YES  | 主键                        |
| parent_id | bigint(20)   | NO   | 父级主键                    |
| name      | varchar(63)  | YES  | 角色名称                    |
| sequence  | int(4)       | YES  | 序号                        |
| path      | varchar(63)  | YES  | 路由父级路径                |
| hidden    | bit(1)       | YES  | 是否隐藏路由，菜单栏隐藏    |
| component | varchar(127) | YES  | 组件路径，去除父级路径      |
| router    | bit(1)       | YES  | 是否是路由 true 是 false 否 |
| redirect  | varchar(31)  | NO   | 重定向路径                  |
| icon      | varchar(31)  | NO   | 图标名称                    |

`角色与菜单关联关系表t_role_menu`

| 名      | 类型       | 必填 | 注释     |
| ------- | ---------- | ---- | -------- |
| id      | bigint(20) | YES  | 主键     |
| role_id | bigint(20) | YES  | 角色主键 |
| menu_id | bigint(20) | YES  | 菜单主键 |

`系统用户与角色表关联关系表t_sys_role`

| 名          | 类型       | 必填 | 注释         |
| ----------- | ---------- | ---- | ------------ |
| id          | bigint(20) | YES  | 主键         |
| role_id     | bigint(20) | YES  | 角色主键     |
| sys_user_id | bigint(20) | YES  | 登录账户主键 |

`资源表t_resource`

| 名           | 类型         | 必填 | 注释                  |
| ------------ | ------------ | ---- | --------------------- |
| id           | bigint(20)   | YES  | 主键                  |
| parent_id    | bigint(20)   | NO   | 父级主键              |
| name         | varchar(63)  | YES  | 接口名称              |
| resource_url | varchar(127) | YES  | 接口访问地址          |
| biz_type     | int(4)       | YES  | 资源类型0 接口 1 按钮 |

`角色与访问资源关联关系表t_role_resource`

| 名          | 类型       | 必填 | 注释     |
| ----------- | ---------- | ---- | -------- |
| id          | bigint(20) | YES  | 主键     |
| role_id     | bigint(20) | YES  | 角色主键 |
| resource_id | bigint(20) | YES  | 资源主键 |

`部门表t_dept`

| 名               | 类型        | 必填 | 注释         |
| ---------------- | ----------- | ---- | ------------ |
| id               | bigint(20)  | YES  | 主键         |
| parent_id        | bigint(20)  | NO   | 父级主键     |
| manager_id       | bigint(20)  | NO   | 部门经理主键 |
| name             | varchar(63) | YES  | 部门名称     |
| established_time | datetime    | YES  | 成立时间     |

`职位等级表t_grade`

| 名   | 类型        | 必填 | 注释     |
| ---- | ----------- | ---- | -------- |
| id   | bigint(20)  | YES  | 主键     |
| name | varchar(63) | YES  | 职级名称 |

`岗位表t_post`

| 名        | 类型        | 必填 | 注释     |
| --------- | ----------- | ---- | -------- |
| id        | bigint(20)  | YES  | 主键     |
| parent_id | bigint(20)  | NO   | 父级主键 |
| name      | varchar(63) | YES  | 岗位名称 |

### 开放平台表设计

`开放账户表t_open_account`

| 名         | 类型         | 必填 | 注释                      |
| ---------- | ------------ | ---- | ------------------------- |
| id         | bigint(20)   | YES  | 主键                      |
| name       | varchar(63)  | YES  | 应用名称                  |
| status     | int(4)       | YES  | 状态 0 开启 1 禁用 2 冻结 |
| secure_id  | varchar(63)  | YES  | secureId                  |
| secure_key | varchar(127) | YES  | secureKey                 |

`资源表t_open_resource`

| 名           | 类型         | 必填 | 注释         |
| ------------ | ------------ | ---- | ------------ |
| id           | bigint(20)   | YES  | 主键         |
| name         | varchar(63)  | YES  | 接口名称     |
| resource_url | varchar(127) | YES  | 接口访问地址 |

`应用与访问资源关联关系表t_open_account_resource`

| 名          | 类型       | 必填 | 注释     |
| ----------- | ---------- | ---- | -------- |
| id          | bigint(20) | YES  | 主键     |
| account_id  | bigint(20) | YES  | 账户主键 |
| resource_id | bigint(20) | YES  | 资源主键 |

### 分布式主键表设计

`Id 号段器t_leaf_alloc`

| 名          | 类型         | 必填 | 注释     |
| ----------- | ------------ | ---- | -------- |
| biz_tag     | bigint(128)  | YES  | 主键     |
| max_id      | bigint(20)   | YES  | 最大步长 |
| step        | int(11)      | YES  | 步长     |
| description | varchar(127) | NO   | 号段说明 |
| update_time | timestamp    | YES  | 更新时间 |
| author      | varchar(127) | NO   | 作者     |

