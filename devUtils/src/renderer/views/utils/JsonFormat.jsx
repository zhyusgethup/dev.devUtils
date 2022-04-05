import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button } from 'antd';
import {Base64} from 'js-base64';
import { request } from '../../commonApi/request';


class JsonFormat extends Component {

  constructor(props) {
    super(props);
    this.state = {
      value: '',
      loadings: []
    };
    this.onChange = this.onChange.bind(this);
    this.submit = this.submit.bind(this);
  }

  onChange = ({ target: { value } }) => {
    this.setState({ value });
  };

  submit() {
    this.setState({
      loadings: [true]
    });
    let value = Base64.encode(this.state.value);
    request.post('/json/format', {
      encode: 'base64',
      value: value
    }).then(res => {
      console.log(res);
    }).finally(() => this.setState({
      loadings: []
    }));
  };


  render() {
    return (
      <div>
        <h1 style={{ 'text-align': 'center' }}>请输入json字符串</h1>
        <TextArea
          value={this.value}
          onChange={this.onChange}
          placeholder='Controlled autosize'
          autoSize={{ minRows: 15, maxRows: 15 }}
        />
        <div>
          <br />
          <Button type='primary' size='large' style={{ display: 'block', margin: '0 auto' }}
                  loading={this.state.loadings[0]} onClick={this.submit}>
            提交
          </Button>
        </div>

      </div>
    );
  }
}

export default JsonFormat;
