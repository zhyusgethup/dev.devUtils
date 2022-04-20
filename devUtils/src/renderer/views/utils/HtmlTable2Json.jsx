import React, { Component, useState } from 'react';
import { InboxOutlined } from '@ant-design/icons';
import { Button, Form, Upload, Input, message, Divider, Table } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import { BASE_URL } from '../../config/RequestConstant';
import TextArea from 'antd/es/input/TextArea';
import { Base64 } from 'js-base64';


function HtmlTable2Json() {

  const [fileList, setFileList] = useState([]);
  const [uploading, setUploading] = useState(false);
  const [uploadVisual, setUploadVisual] = useState(true);
  const [text, setText] = useState('');

  function handleUpload() {
    const formData = new FormData();
    fileList.forEach(file => {
      formData.append('files', file);
    });
    setUploading(true);
    // You can use any AJAX library you like
    fetch(BASE_URL + '/html/html2Json', {
      method: 'POST',
      body: formData
    }).then(res => res.json())
      .then((json) => {
        message.success('upload successfully.');
        console.log(json);
        setUploadVisual(false);
        setText(Base64.decode(json.data));
      })
      .catch(() => {
        message.error('upload failed.');
      })
      .finally(() => {
        // setFileList([]);
        setUploading(false);
      });
  }

  function renderTextArea() {
    return (<TextArea
      value={text}
      placeholder='Controlled autosize'
      autoSize={{ minRows: 3, maxRows: 20 }}
    />);
  }

  const { Dragger } = Upload;
  const props = {
    name: 'files',
    multiple: true,
    beforeUpload: file => {
      if(file.size > 1024 * 1024) {
        message.error("文件大小不能超过1M");
        return false;
      }
      if (file.name.endsWith('.html')) {
        setFileList(fileList => [...fileList, file]);
        return false;
      }
      message.error('只能上传html文件');
      return false;
    },
    onDrop(e) {
      return false;
    },
    onRemove(file) {
      console.log(file.uid);
      let newList = [];
      for (let i = 0; i < fileList.length; i++) {
        if (file.uid != fileList[i].uid) {
          newList.push(fileList[i]);
        }
      }
      setFileList(newList);
    },
    fileList
  };
  return (
    <>
      <div
        className={uploadVisual ? 'visible' : 'hidden'}
      >
        <Dragger {...props}>
          <p className='ant-upload-drag-icon'>
            <InboxOutlined />
          </p>
        </Dragger>
        <div style={{ textAlign: 'center' }}>
          <Button
            onClick={handleUpload}
            size={'large'}
            disabled={fileList.length === 0}
            loading={uploading}
            style={{ marginTop: 16 }}
          >
            {uploading ? 'Uploading' : 'Start Upload'}
          </Button>
        </div>
      </div>
      <div className={uploadVisual ? 'hidden' : 'visible'}>
        {renderTextArea()}
      </div>
      <div>
        <Button onClick={() => location.reload()}>重置</Button>
      </div>
    </>
  );
}

export default HtmlTable2Json;
