import React, { Component } from 'react';
import './root.css';
import { HashRouter, Route, Routes } from 'react-router-dom';
import MenuRegister from '../../MenuRegister';
import JavaParse from '../utils/JavaParse';
import Hello from '../Hello';
import JsonFormat from '../utils/JsonFormat';
import Ddl2Entity from '../utils/Ddl2Entity';
import HtmlTable2Json from '../utils/HtmlTable2Json';

/**
 * 在这里注册路由
 * 这里包名index必须配合下面方式引用, 别的完蛋. 像本文件头部的import方式不能命名为index包, indexx都可以的
 * let Index = lazy(() => import("./views/index/Index.jsx"));
 *
 * react-router-waiter 路由守护仓库
 */
// eslint-disable-next-line react/prefer-stateless-function
class MyRouter extends Component {
  render() {
    return (
      <HashRouter>
        <Routes>
          <Route path="/" element={<MenuRegister />}>
            <Route path="javaParse" element={<JavaParse />} />
            <Route path="hello" element={<Hello />} />
            <Route path="jsonFormat" element={<JsonFormat/>} />
            <Route path="ddl2Entity" element={<Ddl2Entity/>} />
            <Route path="htmlTable2Json" element={<HtmlTable2Json/>} />
          </Route>
        </Routes>
      </HashRouter>
    );
  }
}

export default MyRouter;
