import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button, message} from 'antd';
import { Base64 } from 'js-base64';
import { request } from '../../commonApi/request';
import ClipboardJS from 'clipboard';
import SimpleUtilPage from './SimpleUtilPage';

const struct ={
  url : '/json/format',
  demoValue: '{"data":1}'
}
function JsonFormat(){
  return <SimpleUtilPage struct={struct}/>
}

export default JsonFormat;
