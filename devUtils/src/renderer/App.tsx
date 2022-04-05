/**
 * 这里写核心页面
 * @returns {JSX.Element}
 * @constructor
 */
import { Breadcrumb, Menu } from 'antd';
import Layout, { Content, Footer, Header } from 'antd/lib/layout/layout';
import { Outlet, useNavigate } from 'react-router-dom';

function App() {
  let index = 1;
  /**
   * 这里有个钩子用在函数组件的普通方法的示范
   */
  const navigate = useNavigate();

  function turnTo(url: string) {
    navigate(url);
    // console.log(url);
  }

  function renderMenu(key: number, text: string, url: string) {
    return (
      <Menu.Item key={key} onClick={() => turnTo(url)}>
        {text}
      </Menu.Item>
    );
  }

  // function renderMenusInSubMenu(menusData: Array<any>) {
  //   const menus = [];
  //   // eslint-disable-next-line no-restricted-syntax
  //   for (const e of menusData) {
  //     menus.push(renderMenu(index, e.text, e.url));
  //     index += 1;
  //   }
  //   return menus;
  // }

  // function renderSubMenu(subMenusData: any) {
  //   const key = index;
  //   index += 1;
  //   return (
  //     <SubMenu title={subMenusData.title} key={key}>
  //       {renderMenusInSubMenu(subMenusData.menusData)}
  //     </SubMenu>
  //   );
  // }

  function renderMenuWithoutKey(text: string, url: string) {
    const key = index;
    index += 1;
    return renderMenu(key, text, url);
  }

  function renderMenus() {
    const menus = [];
    menus.push(renderMenuWithoutKey('hello', '/hello'));
    menus.push(renderMenuWithoutKey('java解析', '/javaParse'));
    // menus.push(
    //   renderSubMenu({
    //     title: 'CRUD DEMO',
    //     menusData: [
    //       {
    //         text: 'demo1',
    //         url: '/crud1',
    //       },
    //       {
    //         text: 'demo2',
    //         url: '/crud2',
    //       },
    //       {
    //         text: 'demo3',
    //         url: '/crud3',
    //       },
    //     ],
    //   })
    // );
    return menus;
  }

  return (
    <Layout className="layout">
      <Header>
        <div className="logo" />
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
          {renderMenus()}
        </Menu>
      </Header>
      <Content style={{ padding: '0 50px' }}>
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item>Home</Breadcrumb.Item>
          <Breadcrumb.Item>List</Breadcrumb.Item>
          <Breadcrumb.Item>App</Breadcrumb.Item>
        </Breadcrumb>
        <div className="site-layout-content" id="content">
          <Outlet />
        </div>
      </Content>
      <Footer style={{ textAlign: 'center' }}>
        Ant Design ©2021 Created by Ant UED
      </Footer>
    </Layout>
  );
}

export default App;
