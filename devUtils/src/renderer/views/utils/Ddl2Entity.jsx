import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button, message} from 'antd';
import { Base64 } from 'js-base64';
import { request } from '../../commonApi/request';
import ClipboardJS from 'clipboard';
import SimpleUtilPage from './SimpleUtilPage';
const ddlDemo = "CREATE TABLE `test` (\n" +
  "  `id` bigint(20) NOT NULL,\n" +
  "  `mi` mediumint(255) DEFAULT NULL COMMENT '123213',\n" +
  "  `ti` tinyint(255) DEFAULT NULL COMMENT '123123',\n" +
  "  `char` char(255) DEFAULT NULL,\n" +
  "  `v` varchar(255) DEFAULT NULL COMMENT '123123',\n" +
  "  `text` longtext,\n" +
  "  `text2` text,\n" +
  "  `time` time DEFAULT NULL COMMENT '123123',\n" +
  "  `tis` timestamp(5) NULL DEFAULT NULL COMMENT '123123',\n" +
  "  `date` datetime(5) DEFAULT NULL COMMENT '123123',\n" +
  "  PRIMARY KEY (`id`)\n" +
  ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='123123';";

function Ddl2Entity(){
  return <SimpleUtilPage demoValue={ddlDemo} url={'/sql/ddl2Entity'}/>
}

export default Ddl2Entity;
