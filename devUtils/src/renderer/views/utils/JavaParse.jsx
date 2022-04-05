import './javaParse.css';
import React, { Component } from 'react';
import { Upload, message, Button, Form, Progress, Input, Drawer } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import TextArea from 'antd/es/input/TextArea';

const base64 = require('js-base64').Base64;

class JavaParse extends Component {
  constructor(props) {
    super(props);
    this.onClose = this.onClose.bind(this);
    this.showDrawer = this.showDrawer.bind(this);
    this.copyToClip = this.copyToClip.bind(this);
    this.state = {
      percent: 0,
      maxCount: 1,
      currentCount: 0,
      tip: undefined,
      otherParams: {},
      style: {
        color: 'red',
      },
      success: false,
      visible: false,
      ddl: undefined,
    };
  }

  onClose() {
    this.setState({
      visible: false,
    });
  }

  showDrawer() {
    this.setState({
      visible: true,
    });
  }

  /**
   * 复制内容到粘贴板
   * content : 需要复制的内容
   * message : 复制完后的提示，不传则默认提示"复制成功"
   */

  copyToClip() {
    const aux = document.createElement('input');
    aux.setAttribute('value', this.state.ddl);
    document.body.appendChild(aux);
    aux.select();
    if (document.execCommand('copy')) {
      message.success('复制成功');
    }
    document.body.removeChild(aux);
  }

  render() {
    const refThis = this;
    const props = {
      name: 'javaFile',
      action: 'http://localhost:7003/devUtilApi/java/generateSqlByJavaFile',
      headers: {
        platform: 'Web',
      },
      data: this.state.otherParams,
      beforeUpload: (file) => {
        let shouldIgnore = false;
        let errorInfo;
        if (this.state.currentCount >= this.state.maxCount) {
          shouldIgnore = true;
          errorInfo = `目前只能上传${this.state.maxCount}个java文件`;
        }
        if (!file.name.endsWith('.java')) {
          shouldIgnore = true;
          errorInfo = `${file.name}不是java文件`;
        }
        if (shouldIgnore) {
          message.error(errorInfo);
          return Upload.LIST_IGNORE;
        }
        this.setState({ currentCount: this.state.currentCount + 1 });
      },
      onRemove: (file) => {
        this.setState({ currentCount: this.state.currentCount - 1 });
      },

      onChange(info) {
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          const { response } = info.file;
          switch (response.nextCode) {
            case 0:
              message.info(`文件${info.file.name}正在上传`);
              break;
            case 1:
              message.info(response.message);
              refThis.setState({
                percent: response.percent,
                maxCount: refThis.state.maxCount + 1,
                tip: response.message,
                otherParams: {
                  progressId: response.progressId,
                },
              });
              break;
            case 2:
              message.success(`文件${info.file.name}上传成功`);
              // eslint-disable-next-line no-case-declarations
              const ddl = base64.decode(response.data);
              console.log(ddl);
              refThis.setState({
                percent: response.percent,
                tip: response.message,
                style: {
                  color: 'green',
                },
                visible: true,
                ddl,
                success: true,
              });
              break;
            default:
              message.error(response.message);
              refThis.setState({
                tip: response.message,
              });
              break;
          }
        } else if (info.file.status === 'error') {
          message.error(`${info.file.name} file upload failed.`);
        }
      },
    };
    const buttonStyle =
      // eslint-disable-next-line react/destructuring-assignment
      this.state.success && !this.state.visible
        ? {}
        : {
            display: 'none',
          };
    // eslint-disable-next-line react/destructuring-assignment
    const tipStyle = this.state.tip
      ? {}
      : {
          display: 'none',
        };
    const normFile = (e) => { //如果是typescript, 那么参数写成 e: any
      console.log('Upload event:', e);
      if (Array.isArray(e)) {
        return e;
      }
      return e && e.fileList;
    };
    return (
      <div>
        <Form
          name="basic"
          labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          initialValues={{ remember: true }}
          autoComplete="off"
        >
          <Form.Item label="进度条">
            <Progress percent={this.state.percent} steps={5} />
          </Form.Item>

          <Form.Item
            label=".java文件"
            name="javaFile"
            rules={[{ required: true, message: '请选择一个.java文件' }]}
            valuePropName="fileList"
            // 如果没有这一句会报错 Uncaught TypeError: (fileList || []).forEach is not a function
            getValueFromEvent={normFile}
          >
            {/* eslint-disable-next-line react/jsx-props-no-spreading */}
            <Upload {...props}>
              <Button icon={<UploadOutlined />}>Click to Upload</Button>
            </Upload>
          </Form.Item>
          <Form.Item label="提示信息" style={tipStyle}>
            <Input
              value={this.state.tip}
              bordered={false}
              disabled
              style={this.state.style}
            />
          </Form.Item>
          <Form.Item label="打开" style={buttonStyle}>
            <Button type="primary" onClick={this.showDrawer}>
              Open
            </Button>
          </Form.Item>
        </Form>
        <Drawer
          title="sql建表语句"
          placement="right"
          closable={false}
          onClose={this.onClose}
          visible={this.state.visible}
          getContainer={false}
          style={{ position: 'absolute' }}
          width="50%"
          mask="false"
        >
          <div style={{ display: 'flex', 'flexDirection': 'column' }}>
            <div style={{ display: 'flex', justifyContent: 'center' }}>
              <Button type="primary" onClick={this.copyToClip}>
                复制到粘贴板
              </Button>
            </div>
            <div style={{ height: '10px' }} />
            <TextArea
              value={this.state.ddl}
              placeholder="Controlled autosize"
              autoSize={{ minRows: 3, maxRows: 20 }}
            />
          </div>
        </Drawer>
      </div>
    );
  }
}

export default JavaParse;
