import { useCallback, useRef } from 'react';

export default function useLockCallback(callback, deps) {
  const lock = useRef(false);
  return useCallback(async (...args) => {
    if (lock.current) return;
    lock.current = true;
    try {
      const req = await callback(...args);
      lock.current = false;
      return req;
    } catch (e) {
      lock.current = false;
      throw e;
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);
}
