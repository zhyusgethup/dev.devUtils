import React, { Component, useState } from 'react';
import { InboxOutlined } from '@ant-design/icons';
import { Button, Form, Upload, Input, message } from 'antd';
import Dragger from 'antd/es/upload/Dragger';


class HtmlTable2Json extends Component {

  constructor(props) {
    super(props);
    this.state = {
      fileList: [],
      uploading: false
    };
  }


  handleUpload() {
    const formData = new FormData();
    this.state.fileList.forEach(file => {
      formData.append('files[]', file);
    });
    this.setState({ uploading: true });
    // You can use any AJAX library you like
    fetch('https://www.mocky.io/v2/5cc8019d300000980a055e76', {
      method: 'POST',
      body: formData
    })
      .then(res => res.json())
      .then(() => {
        message.success('upload successfully.');
      })
      .catch(() => {
        message.error('upload failed.');
      })
      .finally(() => {
        this.setState({ fileList: [] });
      });
  }

  // const props = {
  //   onRemove: file => {
  //     const index = fileList.indexOf(file);
  //     const newFileList = fileList.slice();
  //     newFileList.splice(index, 1);
  //     setFileList(newFileList);
  //   },
  //   beforeUpload: file => {
  //     console.log(file);
  //     if(file.name.endsWith(".html")) {
  //       setFileList([...fileList, file]);
  //       return false;
  //     }
  //     message.error('只能上传html文件');
  //     return false;
  //   },
  //   fileList
  // };
  // const normFile = (e) => { //如果是typescript, 那么参数写成 e: any
  //   if (Array.isArray(e)) {
  //     return e;
  //   }
  //   return e && e.fileList;
  // };


  render() {
    const { uploading, fileList } = this.state;
    const props = {
      name: 'file',
      multiple: true,
      action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
      beforeUpload: file => {
        console.log(file);
        // if (file.name.endsWith('.html')) {
          console.log('触发了 更新操作:' + this.state.fileList.length);
          this.setState(state => ({
            fileList: [...state.fileList, file],
          }));
          return false;
        // }
        // message.error('只能上传html文件');
        // return false;
      },
      onDrop(e) {
        console.log('Dropped files', e.dataTransfer.files);
        return false;
      },
      fileList
    };

    return (
      <>
        <Dragger {...props}>
          <p className='ant-upload-drag-icon'>
            <InboxOutlined />
          </p>
        </Dragger>
        <Button
          type='primary'
          onClick={this.handleUpload}
          disabled={this.state.fileList.length === 0}
          loading={this.state.uploading}
          style={{ marginTop: 16 }}
        >
          {this.state.uploading ? 'Uploading' : 'Start Upload'}
        </Button>
      </>
    );
  }
}
export default HtmlTable2Json;
