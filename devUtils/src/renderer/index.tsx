import { render } from 'react-dom';
import MyRouter from './views/router/MyRouter';
import 'antd/dist/antd.css';

render(<MyRouter />, document.getElementById('root'));

// calling IPC exposed from preload script
window.electron.ipcRenderer.once('ipc-example', (arg) => {
  // eslint-disable-next-line no-console
  console.log(arg);
});
window.electron.ipcRenderer.myPing();
