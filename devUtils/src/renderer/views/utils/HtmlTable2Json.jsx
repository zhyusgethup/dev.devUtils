import React, { Component, useState } from 'react';
import { InboxOutlined } from '@ant-design/icons';
import { Button, Form, Upload, Input, message, Divider, Table } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import { BASE_URL } from '../../config/RequestConstant';
import TableUtil from '../../utils/TableUtil';


function HtmlTable2Json() {

  const [fileList, setFileList] = useState([]);
  const [uploading, setUploading] = useState(false);
  const [uploadVisual, setUploadVisual] = useState(true);
  const [tables, setTables] = useState([]);

  function handleUpload() {
    const formData = new FormData();
    fileList.forEach(file => {
      formData.append('files', file);
    });
    setUploading(true);
    // You can use any AJAX library you like
    fetch(BASE_URL + '/html/uploadHtml', {
      method: 'POST',
      body: formData
    }).then(res => res.json())
      .then((json) => {
        message.success('upload successfully.');
        console.log(json);
        setUploadVisual(false);
        setTables(json.data);
      })
      .catch(() => {
        message.error('upload failed.');
      })
      .finally(() => {
        // setFileList([]);
        setUploading(false);
      });
  }
  function renderTable(table, key) {
    return (<div key={key}><Divider>{table.tableTitle}</Divider><Table dataSource={TableUtil.wrapData(table.data)} columns={table.tableColumns} pagination={false} /></div>)
  }

  function renderTables(){
    let tableUIs = [];
    if(tables.length != 0){
      for (let i = 0; i < tables.length; i++) {
        tableUIs.push(renderTable(tables[i], i));
      }
    }
    return tableUIs;
  }

  const { Dragger } = Upload;
  const props = {
    name: 'files',
    multiple: true,
    beforeUpload: file => {
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
    fileList
  };
  return (
    <>
      <div className={uploadVisual ? 'visible' : 'hidden'}>
        <Dragger {...props}>
          <p className='ant-upload-drag-icon'>
            <InboxOutlined />
          </p>
        </Dragger>
        <div style={{ textAlign: 'center' }}>
          <Button
            type='circle'
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
        {renderTables()}
      </div>
    </>
  );
}

export default HtmlTable2Json;
