import axios from 'axios';

import { message } from 'antd';
import CrudMessageHandler from './CrudMessageHandler';
import { BASE_URL, TIMEOUT } from '../config/RequestConstant';

const instance = axios.create({
  baseURL: BASE_URL,
  timeout: TIMEOUT
});

//添加拦截
instance.interceptors.request.use(config => {
  config.headers.Authorization = `Basic YWRtaW46MTIzNDU2`;
  return config;
}, error => {
  error = CrudMessageHandler.recognitionAndWrapError(error);
  message.error(error.message);
  return Promise.reject(error);
});

instance.interceptors.response.use(res => {
  if (res.data.code != 0) {
    message.error(res.data.message);
    return Promise.reject(new Error(res.data.message));
  } else {
    return res.data;
  }
}, error => {
  error = CrudMessageHandler.recognitionAndWrapError(error);
  message.error(error.message);
  return Promise.reject(error);
});
instance.postJson = function(url, data) {
  return instance.postJson(url, data);
};

export { instance as request };
