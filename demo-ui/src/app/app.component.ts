import { Component, OnInit } from '@angular/core';
// TODO: Leasing contract
import { Vehicle } from './vehicle';
import { VehicleService } from './vehicle.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'demo-ui';

  vehicles: Vehicle[] = [];

  constructor(private vehicleService: VehicleService) {
  }



  ngOnInit() {
    this.vehicleService.find().subscribe((data: any)  => {
      this.vehicles = [...data.content];
    })
  }

}
