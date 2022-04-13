/**
 * simpleUtilsPage相关定义
 */
declare interface SimpleUtilsPageStruct {
  demoValue: string;
  url: string;
  checkBoxDefinitions: Array<CheckBoxDefinition>;
  radioGroupDefinitions: Array<RadioGroupDefinition>
}

/**
 * checkBox定义
 */
declare interface CheckBoxDefinition {
  label:string;
  /**
   * name 如果没有就取label
   */
  name: string;
  /**
   * 默认值为false
   */
  checked: boolean;
}

/**
 * 单选框组定义
 */
declare  interface RadioGroupDefinition {
  label: string,
  name: string;
  defaultValue: any;
  options: Array<any>
}

