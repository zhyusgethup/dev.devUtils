import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import MenuRegister from '../renderer/MenuRegister';

describe('App', () => {
  it('should render', () => {
    expect(render(<MenuRegister />)).toBeTruthy();
  });
});
