import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  images = ["harrypotter.jpg", "narnia.jpg", "oppenheimer.jpg"].map((n) => `../../../assets/${n}`);

  constructor() { }

  ngOnInit(): void {
  }

}
