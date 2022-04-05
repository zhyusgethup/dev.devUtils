import {message} from "antd";

class CrudMessageHandler {
}
CrudMessageHandler.recognitionAndWrapError = function (error) {
    if (!!error.message) {
        if (typeof (error.message) == 'string') {
            if (error.message.startsWith("timeout")) {
                error.oldMessage = error.message;
                error.message = '网络超时';
            }
        }
    }
    return error;
}
CrudMessageHandler.dealListResult = function (res){
    for (let item of res.data) {
        if (item.status == 0) {
            if (!!item.data) {
                message.success(item.data + ' ' + item.message);
            } else {
                message.success(item.message);
            }
        } else {
            if (!!item.data) {
                message.error(item.data + ' ' + item.message);
            } else {
                message.error(item.message);
            }
        }
    }
}

export default CrudMessageHandler;
