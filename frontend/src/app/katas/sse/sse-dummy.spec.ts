import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SseDummy } from './sse-dummy';

describe('SseDummy', () => {
  let component: SseDummy;
  let fixture: ComponentFixture<SseDummy>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SseDummy]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SseDummy);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
