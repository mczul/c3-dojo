import { C3ClientPipe } from './exhaustive-pipe';

describe('ExhaustivePipe', () => {
  it('create an instance', () => {
    const pipe = new C3ClientPipe();
    expect(pipe).toBeTruthy();
  });
});
