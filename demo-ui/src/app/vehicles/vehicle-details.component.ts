import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';

import { forkJoin, of } from 'rxjs';
import { Brand, Model, VehicleDetails } from './vehicle';
import { VehicleService } from './vehicle.service';
import { BackendError } from '../entity.service';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css'],
})
export class VehicleDetailsComponent implements OnInit {
  vehicle: VehicleDetails | null = null;

  selectedBrand: Brand | null = null;
  selectedModel: Model | null = null;

  brands: Brand[] = [];
  models: Model[] = [];
  modelsByBrandId = new Map<number, Model[]>();
  error?: BackendError;
  formIsSaved = false;

  vehicleForm = this.fb.group({
    version: new FormControl<number>(0, {
      validators: Validators.required,
      nonNullable: true,
    }),
    brandId: new FormControl<number>(0, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    modelId: new FormControl<number>(0, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    modelYear: new FormControl<number>({ value: -1, disabled: true }),
    vin: new FormControl<string | null>(null, {
      validators: [Validators.minLength(17), Validators.maxLength(17)],
    }),
    price: new FormControl<number>(0, {
      validators: [Validators.required, Validators.pattern(/^\d+\.\d{0,2}$/)],
    }),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private vehicleService: VehicleService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const vehicleId = idParam === 'new' ? null : Number(idParam);
    forkJoin({
      modelsResponse: this.vehicleService.getModels(),
      detailsResponse: vehicleId
        ? this.vehicleService.getDetails(vehicleId)
        : of(this.vehicle),
    }).subscribe((res) => {
      this.updateBrandsAndModels(res['modelsResponse']);
      if (res['detailsResponse']) {
        this.updateForm(res['detailsResponse']);
      }
    });
  }

  private updateBrandsAndModels(data: (Model & Brand)[]): void {
    const brandsMap = new Map<number, Brand>();
    this.modelsByBrandId = new Map<number, Model[]>();
    data.forEach((item) => {
      brandsMap.set(item.brandId, { ...item });
      const model: Model = { ...item };
      const mapEntry = this.modelsByBrandId.get(item.brandId);
      if (!mapEntry) {
        this.modelsByBrandId.set(item.brandId, [model]);
      } else {
        mapEntry.push(model);
      }
    });
    this.brands = [...brandsMap.values()];
  }

  private updateForm(vehicle: VehicleDetails): void {
    this.vehicle = vehicle;
    this.selectedBrand = { ...vehicle };
    this.selectedModel = { ...vehicle };

    const modelsMap = new Map<number, Model>();
    this.modelsByBrandId.get(vehicle.brandId)?.forEach((m) => {
      modelsMap.set(m.modelId, m);
    });

    this.vehicleForm.patchValue(vehicle);
    // Do not allow to change model and brand for existing vehicles
    if (vehicle.id) {
      this.vehicleForm.get('brandId')?.disable();
      this.vehicleForm.get('modelId')?.disable();
    }
    this.models = [...modelsMap.values()];
  }

  onBrandChange(): void {
    const brandId = this.vehicleForm.get('brandId')?.value;
    if (!brandId) {
      return;
    }
    this.models = [];
    this.modelsByBrandId.get(brandId)?.forEach((m) => {
      this.models.push(m);
    });
  }

  onModelChange(): void {
    const modelId = this.vehicleForm.get('modelId')?.value;
    if (!modelId) {
      this.vehicleForm.patchValue({ modelYear: null });
      return;
    }
    const model = this.models.find((m) => m.modelId == modelId);
    this.vehicleForm.patchValue({ modelYear: model?.modelYear });
  }

  onSubmit(): void {
    this.formIsSaved = false;
    if (!this.vehicleForm.valid) {
      console.error(
        `There are form errors: ${JSON.stringify(this.vehicleForm.errors)}`
      );
      return;
    }
    const vehicleToSave = {
      ...this.vehicle,
      ...(this.vehicleForm.value as VehicleDetails),
    };
    // replace empty VIN with undefined (will be serialized as null)
    if (vehicleToSave.vin === '') {
      vehicleToSave.vin = undefined;
    }
    this.vehicleService.save(vehicleToSave).subscribe(
      (vehicle) => {
        if (vehicleToSave.id) {
          this.updateForm(vehicle);
        } else {
          this.router.navigate(['/vehicles', vehicle.id]);
          this.updateForm(vehicle);
        }
        this.formIsSaved = true;
        this.error = undefined;
      },
      (err) => {
        this.error = err;
      }
    );
  }

  onReset(): void {
    this.vehicleForm.reset();
    if (this.vehicle) {
      this.updateForm(this.vehicle);
    }
  }
}
