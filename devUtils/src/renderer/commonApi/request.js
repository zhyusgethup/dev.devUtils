import axios from 'axios';

import {message} from "antd";
import CrudMessageHandler from "./CrudMessageHandler";

const instance = axios.create({
    baseURL: 'http://localhost:7003/devUtilApi',
    timeout: 3000
})

//添加拦截
instance.interceptors.request.use(config => {
    console.log('请求被拦截')
    config.headers.Authorization = `Basic YWRtaW46MTIzNDU2`;
    return config
}, error => {
    error = CrudMessageHandler.recognitionAndWrapError(error);
    message.error(error.message);
    return Promise.reject(error);
})

instance.interceptors.response.use(res => {
    if (res.data.status != 0) {
        message.error(res.data.message)
        return Promise.reject(new Error(res.data.message))
    } else {
        return res;
    }
}, error => {
    error = CrudMessageHandler.recognitionAndWrapError(error);
    message.error(error.message);
    return Promise.reject(error);
})


export {instance as request};
