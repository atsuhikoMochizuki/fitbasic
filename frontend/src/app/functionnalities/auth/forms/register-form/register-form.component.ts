import { Component, OnInit } from '@angular/core';
import { DatasManagerService } from '../../../../shared/services/datas-manager.service';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: '../../../../../styles.css'
})
export class RegisterFormComponent implements OnInit{
pseudonyme: any;
username: any;
password: any;
onSubmitregister() {
throw new Error('Method not implemented.');
}
  
  isAccountActivationSucceeded!:boolean;
  
  constructor(private dms:DatasManagerService){}
  
  ngOnInit(): void {
    this.dms.isAccountActivationSucceed
      .subscribe((value:boolean)=>{
        this.isAccountActivationSucceeded = value;
      })
  }


}
