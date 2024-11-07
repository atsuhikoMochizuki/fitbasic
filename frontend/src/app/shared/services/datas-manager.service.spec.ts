import { TestBed } from '@angular/core/testing';

import { DatasManagerService } from './datas-manager.service';

describe('DatasManagerService', () => {
  let service: DatasManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatasManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
