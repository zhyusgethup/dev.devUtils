/**
 * 这里写核心页面
 * @returns {JSX.Element}
 * @constructor
 */
import { Menu } from 'antd';
import Layout, { Content, Header } from 'antd/lib/layout/layout';
import SubMenu from 'antd/lib/menu/SubMenu';
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

  function renderMenusInSubMenu(menusData: Array<any>) {
    const menus = [];
    // eslint-disable-next-line no-restricted-syntax
    for (const e of menusData) {
      menus.push(renderMenu(index, e.text, e.url));
      index += 1;
    }
    return menus;
  }

  function renderSubMenu(subMenusData: any) {
    const key = index;
    index += 1;
    return (
      <SubMenu title={subMenusData.title} key={key}>
        {renderMenusInSubMenu(subMenusData.menusData)}
      </SubMenu>
    );
  }

  function renderMenuWithoutKey(text: string, url: string) {
    const key = index;
    index += 1;
    return renderMenu(key, text, url);
  }

  function renderMenus() {
    const menus = [];
    menus.push(renderMenuWithoutKey('hello', '/hello'));
    menus.push(
      renderSubMenu({
        title: '解析',
        menusData: [
          {
            text: 'java实体解析为ddl解析',
            url: '/javaParse',
          }, {
            text: 'JSON格式化',
            url: '/jsonFormat',
          },
        ],
      })
    );
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
        <div className="site-layout-content" id="content">
          <Outlet />
        </div>
      </Content>
    </Layout>
  );
}

export default App;
