import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button, message} from 'antd';
import { Base64 } from 'js-base64';
import { request } from '../../commonApi/request';
import ClipboardJS from 'clipboard';


class SimpleUtilPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      value: props.demoValue,
      loadings: [],
      result: ''
    };
    this.onChange = this.onChange.bind(this);
    this.submit = this.submit.bind(this);
  }

  componentDidMount() {
    const copy = new ClipboardJS('.copy');
    copy.on('success', e => {
      message.success('复制成功');
    });
    copy.on('error', function(e) {
      message.error("复制失败:" + e.message);
    });
  }


  onChange = ({ target: { value } }) => {
    this.setState({ value });
  };

  submit() {
    this.setState({
      loadings: [true]
    });
    let value = Base64.encode(this.state.value);
    let data = {
      arithmetic: 'base64',
      value: value
    };
    request.post(this.props.url, data).then(res => {
      let data = res.data;
      let str = Base64.decode(data);
      this.setState({ result: str });
    }).finally(() => this.setState({
      loadings: []
    }));
  };


  render() {
    return (
      <div>
        <TextArea
          value={this.state.value}
          onChange={this.onChange}
          placeholder='请输入json字符串'
          autoSize={{ minRows: 10, maxRows: 10 }}
        />
        <div style={{ textAlign: 'center' }}>
          <Button type='primary' style={{ margin: '2px' }}
                  loading={this.state.loadings[0]} onClick={this.submit}>
            提交
          </Button>
          <Button type='primary' style={{ margin: '2px' }} data-clipboard-text={this.state.result} className={'copy'}>
            复制
          </Button>
        </div>
        <TextArea
          value={this.state.result}
          placeholder='格式化后的字符串'
          autoSize={{ minRows: 15, maxRows: 15 }}
        />
      </div>
    );
  }
}

export default SimpleUtilPage;
