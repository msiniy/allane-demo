import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { CustomerService } from './customer.service';
import { Customer } from './customer';

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.css'],
})
export class CustomerDetailsComponent implements OnInit {
  customer: Customer | null = null;

  customerForm = this.fb.group({
    version: new FormControl<number>(0, {
      validators: Validators.required,
      nonNullable: true,
    }),
    firstName: new FormControl<string>('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
    lastName: new FormControl<string>('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
    birthDate: new FormControl<Date>(new Date('1970-01-01'), {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const customerId = idParam === 'new' ? null : Number(idParam);
    if (customerId) {
      this.customerService.getDetails(customerId).subscribe((customer) => {
        this.customer = customer;
        this.updateForm(customer);
      });
    }
  }

  updateForm(customer: Customer): void {
    this.customer = customer;
    this.customerForm.patchValue(customer);
  }

  onSubmit(): void {
    if (!this.customerForm.valid) {
      console.error(
        `There are form errors: ${JSON.stringify(this.customerForm.errors)}`
      );
      return;
    }
    const customerToSave = {
      ...this.customer,
      ...(this.customerForm.value as Customer),
    };

    this.customerService.save(customerToSave).subscribe((customer) => {
      if (customerToSave.id) {
        this.updateForm(customer);
      } else {
        this.router.navigate(['/customers', customer.id]);
        this.updateForm(customer);
      }
    });
  }

  onReset(): void {
    this.customerForm.reset();
    if (this.customer) {
      this.updateForm(this.customer);
    }
  }
}
