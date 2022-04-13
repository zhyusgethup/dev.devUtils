import React, { Component } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button, message, Tooltip, Checkbox, Radio, Row, Col, Form } from 'antd';
import { Base64 } from 'js-base64';
import { request } from '../../commonApi/request';
import ClipboardJS from 'clipboard';


class SimpleUtilPage extends Component {

  constructor(props) {
    super(props);
    let checkBoxDefinitions = this.props.struct.checkBoxDefinitions;
    let checkBoxesValues = {};
    if (!!checkBoxDefinitions) {
      for (let item of checkBoxDefinitions) {
        let name = item.name;
        let checked = item.checked;
        if (!name) {
          name = item.label;
        }
        if (!checked) {
          checked = false;
        }
        checkBoxesValues[name] = checked;
      }
    }
    let radioGroupDefinitions = this.props.struct.radioGroupDefinitions;
    let radioGroupValues = {};
    if (!!radioGroupDefinitions) {
      for (let item of radioGroupDefinitions) {
        let defaultValue = item.defaultValue;
        if (!defaultValue) {
          defaultValue = null;
        }
        radioGroupValues[item.name] = defaultValue;
      }
    }
    this.state = {
      value: props.struct.demoValue,
      loadings: [],
      result: '',
      checkBoxesValues: checkBoxesValues,
      radioGroupValues: radioGroupValues
    };
    this.onChange = this.onChange.bind(this);
    this.submit = this.submit.bind(this);
    this.clearContext = this.clearContext.bind(this);
    this.renderCheckBoxes = this.renderCheckBoxes.bind(this);
    this.checkBoxChange = this.checkBoxChange.bind(this);
    this.radioChange = this.radioChange.bind(this);
  }

  componentDidMount() {
    const copy = new ClipboardJS('.copy');
    copy.on('success', e => {
      message.success('复制成功');
    });
    copy.on('error', function(e) {
      message.error('复制失败:' + e.message);
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
      value: value,
      ...this.state.checkBoxesValues,
      ...this.state.radioGroupValues,
    };
    request.post(this.props.struct.url, data).then(res => {
      let data = res.data;
      let str = Base64.decode(data);
      this.setState({ result: str });
    }).finally(() => this.setState({
      loadings: []
    }));
  };

  clearContext() {
    this.setState({ value: '' });
  }

  checkBoxChange(event) {
    let checkBoxesValues = this.state.checkBoxesValues;
    checkBoxesValues[event.target.name] = event.target.checked;
    this.setState({ checkBoxesValues: checkBoxesValues });
  }

  radioChange = e => {
    console.log(e.target.name + ' checked', e.target.value);
    let radioGroupValues = this.state.radioGroupValues;
    radioGroupValues[e.target.name] = e.target.value;
    this.setState({
      radioGroupValues: radioGroupValues
    });
  };

  renderCheckBox(checkBoxDefinition, key) {
    let name = checkBoxDefinition.name;
    let checked = checkBoxDefinition.checked;
    if (!name) {
      name = checkBoxDefinition.label;
    }
    if (!checked) {
      checked = false;
    }
    return <Col span={4} key={key}><Checkbox name={name} onChange={this.checkBoxChange}
                                             defaultChecked={checked}>{checkBoxDefinition.label}</Checkbox></Col>;
  }

  renderCheckBoxes() {
    let checkBoxDefinitions = this.props.struct.checkBoxDefinitions;
    if (!!checkBoxDefinitions) {
      let checkBoxes = [];
      for (let itemKey in checkBoxDefinitions) {
        checkBoxes.push(this.renderCheckBox(checkBoxDefinitions[itemKey], itemKey));
      }
      return (<Row gutter={24}>{checkBoxes}</Row>);
    }
  }

  renderRadio(options) {
    let array = [];
    for (let key in options) {
      let item = options[key];
      array.push(<Radio value={item.value} checked={key == 0} key={key}>{item.label}</Radio>);
    }
    return array;
  }

  renderRadioGroup(radioGroupDefinition, key) {
    return (<Form.Item key={key}
                       label={radioGroupDefinition.label}
    ><Radio.Group name={radioGroupDefinition.name} onChange={this.radioChange}
                  defaultValue={this.state.radioGroupValues[radioGroupDefinition.name]}>
      {this.renderRadio(radioGroupDefinition.options)}
    </Radio.Group>
    </Form.Item>);
  }

  renderRadioGroups() {
    let radioGroupDefinitions = this.props.struct.radioGroupDefinitions;
    if (!!radioGroupDefinitions) {
      let radioGroups = [];
      for (let itemKey in radioGroupDefinitions) {
        radioGroups.push(this.renderRadioGroup(radioGroupDefinitions[itemKey], itemKey));
      }
      return (<Row gutter={24}><Col span={12}>{radioGroups}</Col></Row>);
      // return radioGroups;
    }
  }

  render() {
    return (
      <div>
        <Tooltip placement='top' title={'双击清除内容'}>
          <TextArea
            value={this.state.value}
            onChange={this.onChange}
            onDoubleClick={this.clearContext}
            placeholder='请输入json字符串'
            autoSize={{ minRows: 10, maxRows: 10 }}
          />
        </Tooltip>
        {this.renderCheckBoxes()}
        {this.renderRadioGroups()}

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
