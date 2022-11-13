import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { Observable } from 'rxjs';
import { Brand, Model, VehicleDetails } from './vehicle';
import { VehicleService } from './vehicle.service';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css'],
})
export class VehicleDetailsComponent implements OnInit {
  vehicle: Partial<VehicleDetails> = { version: 0 };

  selectedBrand: Brand | null  = null;

  selectedModel: Model | null = null;

  brands = new Map<number, Brand>();
  models = new Map<number, Model>();
  modelsByBrandId = new Map<number, Set<Model>>();

  vehicleForm = this.fb.group({
    version: new FormControl(0, {
      validators: Validators.required,
      nonNullable: true,
    }),
    brand: new FormControl<number | null>(null, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    model: new FormControl<number | null>(null, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    year: new FormControl<number | null>(
      { value: null, disabled: true },
      {
        validators: [Validators.required],
      }
    ),
    vin: new FormControl<string | null>('', {
      validators: [Validators.minLength(17), Validators.maxLength(17)],
    }),
    price: new FormControl<number | null>(null, {
      validators: [Validators.required],
    }),
  });

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private vehicleService: VehicleService
  ) {}

  ngOnInit(): void {
    this.vehicleService.getModels().subscribe(response => {
      this.brands = new Map<number, Brand>();
      this.modelsByBrandId = new Map<number, Set<Model>>();
      response.forEach(item => {
        this.brands.set(item.brandId, {...item});
        const model: Model = {...item};
        const mapEntry = this.modelsByBrandId.get(item.brandId);
        if (!mapEntry) {
          this.modelsByBrandId.set(item.brandId, new Set<Model>([model]));
        } else {
          mapEntry.add(model);
        }
      })

    });
    const idParam = this.route.snapshot.paramMap.get('id');
    const vehicleId = idParam === 'new' ? null : Number(idParam);

    if (vehicleId) {
      this.vehicleService.getDetails(vehicleId).subscribe((vehicle) => {
        this.vehicle = vehicle;
        this.selectedBrand = {...vehicle};
        this.selectedModel = {...vehicle};
        this.models = new Map<number, Model>();
        this.modelsByBrandId.get(vehicle.brandId)?.forEach(m => {
          this.models.set(m.modelId, m);
        })

        this.vehicleForm.setValue({
          brand: vehicle.brandId,
          model: vehicle.modelId,
          vin: vehicle.vin || null,
          price: vehicle.price || null,
          year: vehicle.modelYear,
          version: vehicle.version || 0,
        });
      });
    }
  }

  onBrandChange(): void {
    const brandId = this.vehicleForm.get('brand')?.value;
    if (!brandId) { return; }
    this.models = new Map<number, Model>();
    this.modelsByBrandId.get(brandId)?.forEach(m => {
      this.models.set(m.modelId, m);
    })
    this.onModelChange();
  }

  onModelChange(): void {
    const modelId = this.vehicleForm.get('model')?.value;
    if (!modelId) {
      this.vehicleForm.patchValue({year: null});
      return;
    }
    const model = this.models.get(modelId);
    this.vehicleForm.patchValue({year: model?.modelYear});
  }

  onSubmit(): void {
    console.log('submit!');
    console.warn(this.vehicleForm.value);
  }
}
