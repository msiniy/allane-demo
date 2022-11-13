import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';

import { Contract, ContractOverview } from './contract';
import { ContractService } from './contract.service';
import { Vehicle } from '../vehicles/vehicle';
import { Customer } from '../customers/customer';
import { forkJoin } from 'rxjs';
import { VehicleService } from '../vehicles/vehicle.service';
import { CustomerService } from '../customers/customer.service';

@Component({
  selector: 'app-contract-details',
  templateUrl: './contract-details.component.html',
  styleUrls: ['./contract-details.component.css']
})
export class ContractDetailsComponent implements OnInit {

  contract: ContractOverview | null = null;
  customers: Customer[] = [];
  vehicles: Vehicle[] = [];

  contractForm = this.fb.group({
    version: new FormControl<number>(0, {
      validators: Validators.required,
      nonNullable: true,
    }),
    contractNumber: new FormControl<number | null>(null, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    monthlyRate: new FormControl<number | null>(null, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    vehicleId: new FormControl<number | null>(null, {
      nonNullable: true,
    }),
    customerId: new FormControl<number | null>(null, {
      nonNullable: true,
    }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private contractService: ContractService,
    private vehicleService: VehicleService,
    private customerService: CustomerService,
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const contractId = idParam === 'new' ? null : Number(idParam);
    if (contractId) {
      this.contractService.getDetails(contractId).subscribe((contract) => {
        this.updateForm(contract);
      });
    } else {
      forkJoin({
        'vehicles': this.vehicleService.findFree(),
        'customers': this.customerService.find(0, 200),
      }).subscribe(res => {
        this.customers = res["customers"].content;
        this.vehicles = res["vehicles"];
      });
    }
  }

  updateForm(contract: ContractOverview): void {
    this.contract = contract;
    this.contractForm.patchValue(contract.contract);

  }

  onSubmit(): void {
    if (!this.contractForm.valid) {
      console.error(
        `There are form errors: ${JSON.stringify(this.contractForm.errors)}`
      );
      return;
    }

    const contractToSave: Contract = {...this.contract?.contract, ...this.contractForm.value as Contract};

    this.contractService.save(contractToSave).subscribe((contract) => {
      if (contractToSave.id) {
        this.updateForm(contract);
      } else {
        this.router.navigate(['/contracts', contract.contract.id]);
        this.updateForm(contract);
      }
    });
  }

  onReset(): void {
    this.contractForm.reset();
    if (this.contract) {
      this.updateForm(this.contract);
    }
  }

  get isNew() : boolean {
    return this.contract?.contract.id === undefined;
  }
}
