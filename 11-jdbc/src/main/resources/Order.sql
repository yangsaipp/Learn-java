/*==============================================================*/
/* Table: b_order                                               */
/*==============================================================*/
drop table if exists b_order;
create table b_order 
(
   order_id             varchar(32)                    not null,
   org_code             varchar(32)                    null,
   sales_user_id        varchar(32)                    null,
   sales_time           timestamp                      null,
   customer_info        varchar(300)                   null,
   amount               numeric(12,2)                  null,
   create_time          timestamp                      null,
   remark 				text							null,
   primary key (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售订单';
