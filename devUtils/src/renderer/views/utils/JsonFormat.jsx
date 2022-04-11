import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button, message} from 'antd';
import { Base64 } from 'js-base64';
import { request } from '../../commonApi/request';
import ClipboardJS from 'clipboard';
import SimpleUtilPage from './SimpleUtilPage';


function JsonFormat(){
  return <SimpleUtilPage demoValue={'{"data":1}'} url={'/json/format'}/>
}

export default JsonFormat;
