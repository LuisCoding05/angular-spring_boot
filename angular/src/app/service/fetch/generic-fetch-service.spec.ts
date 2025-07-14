import { TestBed } from '@angular/core/testing';

import { GenericFetchService } from './generic-fetch-service';

describe('GenericFetchService', () => {
  let service: GenericFetchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenericFetchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
