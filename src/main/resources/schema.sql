/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2018/10/2 17:44:51                           */
/*==============================================================*/


drop table config;

drop table file_info;

drop table groups;

drop table groups_role;

drop table groups_user;

drop table oauth_user;

drop table password;

drop table permission;

drop table role;

drop table role_permission;

drop table run_log;

drop table user_role;

drop table user_token;

drop table users;

/*==============================================================*/
/* Table: config                                                */
/*==============================================================*/
create table config (
   key                  VARCHAR(50)          not null,
   value                TEXT                 not null,
   uid                  INT8                 null,
   description          VARCHAR(255)         not null,
   status               INT2                 not null default 1,
   pkey                 VARCHAR(50)          null,
   constraint PK_CONFIG primary key (key)
);

comment on table config is
'配置表；记录系统、用户配置，当用户ID为NULL或者0时是表示此配置项为系统配置项';

comment on column config.key is
'键';

comment on column config.value is
'值';

comment on column config.uid is
'用户ID';

comment on column config.description is
'描述';

comment on column config.status is
'默认有效;{1:有效;2:无效;}';

comment on column config.pkey is
'上级键名';

/*==============================================================*/
/* Table: file_info                                             */
/*==============================================================*/
create table file_info (
   md5                  CHAR(32)             not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   raw_name             VARCHAR(100)         not null,
   path                 VARCHAR(100)         not null,
   category             VARCHAR(16)          not null,
   status               INT2                 not null default 1,
   constraint PK_FILE_INFO primary key (md5)
);

comment on table file_info is
'文件表';

comment on column file_info.md5 is
'MD5码';

comment on column file_info.add_time is
'添加时间';

comment on column file_info.latest_time is
'最后修改时间';

comment on column file_info.raw_name is
'原文件名称';

comment on column file_info.path is
'路径';

comment on column file_info.category is
'文件类型';

comment on column file_info.status is
'数据状态{1:正常;2:待审核;0:删除}';

/*==============================================================*/
/* Table: groups                                                */
/*==============================================================*/
create table groups (
   id                   SERIAL not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   status               INT2                 not null default 1,
   sid                  INT8                 not null,
   title                VARCHAR(20)          not null,
   code                 VARCHAR(20)          not null,
   pid                  VARCHAR(20)          null,
   sort                 INT2                 not null,
   constraint PK_GROUPS primary key (id)
);

comment on table groups is
'用户组表、部门表';

comment on column groups.id is
'ID';

comment on column groups.add_time is
'添加时间';

comment on column groups.latest_time is
'最后修改时间';

comment on column groups.status is
'数据状态{1:正常;9:删除}';

comment on column groups.sid is
'系统角色ID';

comment on column groups.title is
'标题';

comment on column groups.code is
'代码';

comment on column groups.pid is
'上级分组';

comment on column groups.sort is
'排序号，升序排列，最大排序号不超过127，默认须按添加顺序设置序号';

/*==============================================================*/
/* Table: groups_role                                           */
/*==============================================================*/
create table groups_role (
   role_id              INT8                 null,
   groups_id            INT8                 null
);

comment on table groups_role is
'组角色';

comment on column groups_role.role_id is
'ID';

comment on column groups_role.groups_id is
'ID';

/*==============================================================*/
/* Table: groups_user                                           */
/*==============================================================*/
create table groups_user (
   uid                  INT8                 null,
   groups_id            INT8                 null
);

comment on table groups_user is
'组用户表,用户组包含的用户';

comment on column groups_user.uid is
'ID';

comment on column groups_user.groups_id is
'ID';

/*==============================================================*/
/* Table: oauth_user                                            */
/*==============================================================*/
create table oauth_user (
   oauth_group          CHAR(2)              not null,
   oauth_platform       INT2                 not null default 1,
   open_id              VARCHAR(128)         not null,
   uid                  INT8                 not null,
   union_id             VARCHAR(128)         null,
   is_subscribe         INT2                 null default 1,
   subscribe_time       DATE                 null,
   access_token         VARCHAR(128)         null,
   token_expire         DATE                 null,
   constraint PK_OAUTH_USER primary key (oauth_group, oauth_platform, open_id)
);

comment on table oauth_user is
'第三方用户表';

comment on column oauth_user.oauth_group is
'第三方平台组织{TX:腾讯;AL:阿里;BD:百度;}';

comment on column oauth_user.oauth_platform is
'第三方平台类型{1：微信}';

comment on column oauth_user.open_id is
'开放ID';

comment on column oauth_user.uid is
'ID';

comment on column oauth_user.union_id is
'关联ID,同一个公司不同的平台一般会产生一个共同的ID，此ID即unionId，如腾讯的微信、QQ等平台';

comment on column oauth_user.is_subscribe is
'订阅标识，微信公众号使用{1:已订阅;2:未订阅;}';

comment on column oauth_user.subscribe_time is
'订阅时间，微信公众号使用';

comment on column oauth_user.access_token is
'访问令牌';

comment on column oauth_user.token_expire is
'令牌过期时间';

/*==============================================================*/
/* Table: password                                              */
/*==============================================================*/
create table password (
   uid                  INT8                 not null,
   pwd                  VARCHAR(128)         not null,
   category             INT2                 not null default 1,
   add_time             DATE                 not null,
   status               INT2                 not null default 1
);

comment on table password is
'密码表';

comment on column password.uid is
'用户ID';

comment on column password.pwd is
'密码建议为用户输入的密码+用户的安全码，然后采用不可逆加密方式进行加密';

comment on column password.category is
'密码类型{1:登录密码;}';

comment on column password.add_time is
'添加时间';

comment on column password.status is
'数据状态，同一个用户同一类型的密码在正常状态下应该只有一个密码{1:正常;0:删除}';

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission (
   id                   SERIAL not null,
   sid                  INT8                 not null,
   category             INT2                 not null default 1,
   module               VARCHAR(20)          not null,
   title                VARCHAR(20)          not null,
   description          VARCHAR(128)         null,
   expression           VARCHAR(128)         not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   status               INT2                 not null,
   constraint PK_PERMISSION primary key (id)
);

comment on table permission is
'权限表';

comment on column permission.id is
'ID';

comment on column permission.sid is
'系统角色ID';

comment on column permission.category is
'权限类型{1:操作权限；2：接口权限}';

comment on column permission.module is
'模块标题';

comment on column permission.title is
'权限标题';

comment on column permission.description is
'权限描述';

comment on column permission.expression is
'权限表达式';

comment on column permission.add_time is
'添加时间';

comment on column permission.latest_time is
'最后修改时间';

comment on column permission.status is
'数据状态{1:正常;9:删除}';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role (
   id                   SERIAL not null,
   sid                  INT8                 not null,
   title                VARCHAR(20)          not null,
   permission           INT4                 not null default 0,
   sort                 INT2                 not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   status               INT2                 not null default 1,
   constraint PK_ROLE primary key (id)
);

comment on table role is
'角色表';

comment on column role.id is
'ID';

comment on column role.sid is
'系统角色ID';

comment on column role.title is
'角色标题';

comment on column role.permission is
' 简易统一权限，整个系统分为四个权限{0：无权限，查看除外、1：编辑、2：删除、4：审核、8：发布}
所有后台人员都可以查看所有资料；编辑权限的人员可添加修改数据，删除权限的人员可以删除数据，编辑和删除数据都需要经过审核人员审核通过后才执行相应操作，发布权限的人员在审核通过后可以发布数据到前台显示';

comment on column role.sort is
'排序号，升序排列，最大排序号不超过127，默认须按添加顺序设置序号';

comment on column role.add_time is
'添加时间';

comment on column role.latest_time is
'最后修改时间';

comment on column role.status is
'数据状态{1:正常;9:删除}';

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission (
   role_id              INT8                 not null,
   permission_id        INT8                 not null,
   constraint PK_ROLE_PERMISSION primary key (role_id, permission_id)
);

comment on table role_permission is
'角色权限表，用户分组表，部门表';

comment on column role_permission.role_id is
'ID';

comment on column role_permission.permission_id is
'ID';

/*==============================================================*/
/* Table: run_log                                               */
/*==============================================================*/
create table run_log (
   id                   SERIAL not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   status               INT2                 not null default 1,
   type                 INT2                 not null default 1,
   uid                  INT8                 not null,
   account              VARCHAR(255)         not null,
   content              VARCHAR(255)         not null,
   ip                   VARCHAR(255)         not null,
   constraint PK_RUN_LOG primary key (id)
);

comment on table run_log is
'运行日志';

comment on column run_log.id is
'ID';

comment on column run_log.add_time is
'添加时间';

comment on column run_log.latest_time is
'最后修改时间';

comment on column run_log.status is
'数据状态{1:正常;0:删除}';

comment on column run_log.type is
'日志类型{1:系统日志（系统自动处理的操作）；2：登录日志（用户登录日志）；3：用户日志（用户操作日志）；4：接口调用日志（调用API的日志）}';

comment on column run_log.uid is
'用户ID';

comment on column run_log.account is
'用户账号, 保存格式如下:
账号;手机号;邮箱;姓名';

comment on column run_log.content is
'内容';

comment on column run_log.ip is
'操作IP';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role (
   uid                  INT8                 not null,
   role_id              INT8                 not null
);

comment on table user_role is
'记录用户、用户组、角色，如果用户组ID或者对应用户组的组织机构ID为NULL表示用户为系统用户';

comment on column user_role.uid is
'ID';

comment on column user_role.role_id is
'ID';

/*==============================================================*/
/* Table: user_token                                            */
/*==============================================================*/
create table user_token (
   id                   CHAR(46)             not null,
   uid                  INT8                 null,
   category             INT2                 not null default 1,
   effective_time       DATE                 not null,
   expire_time          DATE                 not null,
   add_time             DATE                 not null,
   status               INT2                 not null default 1,
   constraint PK_USER_TOKEN primary key (id)
);

comment on table user_token is
'用户令牌表';

comment on column user_token.id is
'ID';

comment on column user_token.uid is
'ID';

comment on column user_token.category is
'令牌类型{1: 邮箱重置密码令牌;}';

comment on column user_token.effective_time is
'生效时间';

comment on column user_token.expire_time is
'过期时间';

comment on column user_token.add_time is
'添加时间';

comment on column user_token.status is
'数据状态{1:正常;2:禁用;0:删除}，令牌只有在状态正常且令牌没有过期才可使用';

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users (
   id                   SERIAL not null,
   add_time             DATE                 not null,
   latest_time          DATE                 not null,
   status               INT2                 not null default 1,
   sid                  INT8                 not null,
   account              VARCHAR(32)          null,
   email                VARCHAR(32)          null,
   phone                VARCHAR(32)          null,
   idnumber             CHAR(18)             null,
   safety_code          CHAR(4)              not null,
   constraint PK_USERS primary key (id)
);

comment on table users is
'用户表';

comment on column users.id is
'ID';

comment on column users.add_time is
'添加时间';

comment on column users.latest_time is
'最后修改时间';

comment on column users.status is
'数据状态{0:删除;1:正常;2:待审核;3:禁用;},删除的账号可以重新注册';

comment on column users.sid is
'系统角色ID';

comment on column users.account is
'账号, 只能包含数字字母下划线横杠且必须已字母开头';

comment on column users.email is
'邮箱';

comment on column users.phone is
'手机号';

comment on column users.idnumber is
'身份证号码';

comment on column users.safety_code is
'安全码，可以组合在密码中用于混淆密码，降低被撞库的可能性，由系统自动生成';

alter table config
   add constraint FK_CONFIG_USER_DICT_USERS foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

alter table groups_role
   add constraint FK_GROUPS_R_GROUP_ROL_GROUPS foreign key (groups_id)
      references groups (id)
      on delete restrict on update restrict;

alter table groups_role
   add constraint FK_GROUPS_R_ROLE_GROU_ROLE foreign key (role_id)
      references role (id)
      on delete restrict on update restrict;

alter table groups_user
   add constraint FK_GROUPS_U_GROUP_USE_GROUPS foreign key (groups_id)
      references groups (id)
      on delete restrict on update restrict;

alter table groups_user
   add constraint FK_GROUPS_U_USER_GROU_USERS foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

alter table oauth_user
   add constraint FK_OAUTH_US_USER_OAUT_USERS foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

alter table password
   add constraint FK_PASSWORD_USER_PASS_USERS foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

alter table role_permission
   add constraint FK_PERMISSION_ROLE foreign key (permission_id)
      references permission (id)
      on delete restrict on update restrict;

alter table role_permission
   add constraint FK_ROLE_PERMISSION foreign key (role_id)
      references role (id)
      on delete restrict on update restrict;

alter table user_role
   add constraint FK_USER_ROL_ROLE_USER_ROLE foreign key (role_id)
      references role (id)
      on delete restrict on update restrict;

alter table user_role
   add constraint FK_USER_ROLE foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

alter table user_token
   add constraint FK_USER_TOK_USER_USER_USERS foreign key (uid)
      references users (id)
      on delete restrict on update restrict;

