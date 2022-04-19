class TableUtil {
}

TableUtil.wrapData = function (data, id) {
    if (data.length != 0) {
        let i = 1;
        for (let col of data) {
            col.index = i++;
            if (!!id) {
                col.key = col[id];
            } else {
                col.id = i;
                col.key = i;
            }
        }
    }
    return data;
}

export default TableUtil;