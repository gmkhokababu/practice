import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtLedger } from './debt-ledger';

describe('DebtLedger', () => {
  let component: DebtLedger;
  let fixture: ComponentFixture<DebtLedger>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DebtLedger],
    }).compileComponents();

    fixture = TestBed.createComponent(DebtLedger);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
