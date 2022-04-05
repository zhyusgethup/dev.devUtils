import React, { Component } from 'react';
import './root.css';
import { HashRouter, Route, Routes } from 'react-router-dom';
import App from '../../App';
import JavaParse from '../utils/JavaParse';
import Hello from '../Hello';

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
          <Route path="/" element={<App />}>
            <Route path="javaParse" element={<JavaParse />} />
            <Route path="hello" element={<Hello />} />
          </Route>
        </Routes>
      </HashRouter>
    );
  }
}

export default MyRouter;
