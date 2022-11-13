import { Component, OnInit, } from '@angular/core';
import { EntityListComponent } from '../entity-list.component';
import { Vehicle, VehicleDetails } from './vehicle';
import { VehicleService } from './vehicle.service';

@Component({
  selector: 'vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css'],
})
export class VehicleListComponent extends EntityListComponent<Vehicle, VehicleDetails> implements OnInit {

  constructor(vehicleService: VehicleService) {
    super(vehicleService);
  }

  ngOnInit(): void {
    this.switchPage(0);
  }
}
